package com.vignesh.transactionaldemo.service;

import com.vignesh.transactionaldemo.dao.EmployeeDao;
import com.vignesh.transactionaldemo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> findAll(){
        return employeeDao.findAll();
    }

    public Employee findById(long id) {
        return employeeDao.findById(id);
    }

    public Employee save(Employee employee){
        long id = employeeDao.save(employee);
        employee.setId(id);
        return employee;
    }

    @Transactional
    public Employee update(long id,Employee employee) throws EmployeeNotFoundException {
        Employee employeeFound = null;
        try {
            employeeFound = employeeDao.findByIdForUpdate(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Employee with id : {} not found",id);
            throw new EmployeeNotFoundException(id);
        }
        employeeFound.setFirstName(employee.getFirstName());
        employeeFound.setLastName(employee.getLastName());
        employeeFound.setDob(employee.getDob());
        employeeFound.setSalary(employee.getSalary());
        int updated = employeeDao.update(employeeFound);
        log.debug("Updated : {}",updated);
        return employeeFound;
    }

    public void deleteById(long id) throws EmployeeNotFoundException {
        int deleted = employeeDao.deleteById(id);
        if(deleted < 1) {
            log.error("Employee with id : {} not found",id);
            throw new EmployeeNotFoundException(id);
        }
    }
}
