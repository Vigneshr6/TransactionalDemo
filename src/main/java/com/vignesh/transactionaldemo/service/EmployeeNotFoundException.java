package com.vignesh.transactionaldemo.service;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(long message) {
        super(String.format("Employee with id : {} not found",message));
    }
}
