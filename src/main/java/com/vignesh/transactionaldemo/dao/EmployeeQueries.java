package com.vignesh.transactionaldemo.dao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "sql.employee")
@Component
@Data
public class EmployeeQueries {
    private String findAll;
    private String findById;
    private String findByIdForUpdate;
    private String save;
    private String update;
    private String delete;
}
