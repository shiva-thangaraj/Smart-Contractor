package com.smartcontractor.controller;

import com.smartcontractor.common.ApiResponse;
import com.smartcontractor.model.Company;
import com.smartcontractor.model.Employee;
import com.smartcontractor.model.PaymentDetails;
import com.smartcontractor.service.CompanyService;
import com.smartcontractor.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createEmployee(
            @RequestParam String userId,
            @RequestParam String companyId,
            @RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(userId, companyId, employee);
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "Employee created successfully", createdEmployee), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("User not found".equals(e.getMessage()) || "Company not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Employee creation failed", e.getMessage()), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Employee creation failed", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<?>> deleteEmployee(
            @RequestParam String employeeId) {
        try {
            if (employeeService.deleteEmployee(employeeId)) {
                return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Employee deleted successfully", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Employee deletion failed", "Employee not found"), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Employee deletion failed", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/employee")
    public ResponseEntity<ApiResponse<?>> getEmployee(@RequestParam String employeeId) {

        try {

            Employee employee = employeeService.getEmployeeById(employeeId);

            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Company found", employee), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Company Not Found", "User not found"), HttpStatus.NOT_FOUND);
        }


    }


    @GetMapping("/get/allemployee")
    public ResponseEntity<ApiResponse<?>> getAllEmployee(@RequestParam String companyId) {

        try {

            List<Employee> employee = employeeService.getAllEmployeesByCompany(companyId);

            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Company found", employee), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Company Not Found", "User not found"), HttpStatus.NOT_FOUND);
        }


    }

}

