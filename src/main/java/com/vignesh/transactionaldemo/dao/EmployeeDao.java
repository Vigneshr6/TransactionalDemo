package com.vignesh.transactionaldemo.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vignesh.transactionaldemo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EmployeeDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private EmployeeQueries employeeQueries;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Employee> findAll() {
        List<Map<String,Object>> list = namedParameterJdbcTemplate.queryForList(employeeQueries.getFindAll(), Collections.EMPTY_MAP);
        return list.stream().map(map -> objectMapper.convertValue(map,Employee.class)).collect(Collectors.toList());
    }

    public Employee findById(long id) {
        Map<String, Object> emp = namedParameterJdbcTemplate.queryForMap(employeeQueries.getFindById(), Collections.singletonMap("id", id));
        if(emp.isEmpty()) {
            return null;
        }
        return objectMapper.convertValue(emp,Employee.class);
    }

    public Employee findByIdForUpdate(long id) {
        return namedParameterJdbcTemplate.queryForObject(employeeQueries.getFindByIdForUpdate(), Collections.singletonMap("id", id), BeanPropertyRowMapper.newInstance(Employee.class));
    }

    public long save(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, ?> paramMap = objectMapper.convertValue(employee, Map.class);
        namedParameterJdbcTemplate.update(employeeQueries.getSave(), new MapSqlParameterSource(paramMap),keyHolder,new String[]{"id"});
        return keyHolder.getKeyAs(Long.class);
    }

    public int update(Employee employee) {
        Map<String, ?> paramMap = objectMapper.convertValue(employee, Map.class);
        return namedParameterJdbcTemplate.update(employeeQueries.getUpdate(), paramMap);
    }

    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(employeeQueries.getDelete(), Collections.singletonMap("id", id));
    }

}
