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

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private EmployeeQueries employeeQueries;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Employee> findAll() {
        return namedParameterJdbcTemplate.query(employeeQueries.getFindAll(), Collections.EMPTY_MAP, BeanPropertyRowMapper.newInstance(Employee.class));
    }

    public Employee findById(long id) {
        return namedParameterJdbcTemplate.queryForObject(employeeQueries.getFindById(), Collections.singletonMap("id", id), BeanPropertyRowMapper.newInstance(Employee.class));
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
