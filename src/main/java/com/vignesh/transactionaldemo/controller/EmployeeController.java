package com.vignesh.transactionaldemo.controller;

import com.vignesh.transactionaldemo.model.Employee;
import com.vignesh.transactionaldemo.service.EmployeeNotFoundException;
import com.vignesh.transactionaldemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> all = employeeService.findAll();
        log.debug("count : " + all.size());
        return all;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee) {
        log.debug("Employee : {}", employee);
        employeeService.save(employee);
        return employee;
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee employeeFound = employeeService.update(id, employee);
            return new ResponseEntity(employeeFound, OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity(NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            employeeService.deleteById(id);
            return new ResponseEntity(OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity(NOT_FOUND);
        }
    }
}
