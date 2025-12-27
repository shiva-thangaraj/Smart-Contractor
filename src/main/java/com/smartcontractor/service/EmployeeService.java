package com.smartcontractor.service;

import com.smartcontractor.model.Employee;
import com.smartcontractor.model.PaymentDetails;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(String userId, String companyId, Employee employee);
    boolean deleteEmployee(String employeeId);
    Employee getEmployeeById(String employeeId);
    List<Employee> getAllEmployees(String companyId);
    List<Employee> getAllEmployeesByCompany(String companyId);

    Employee updateEmployee(String employeeId, Employee employee);
}
