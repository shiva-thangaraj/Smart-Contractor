package com.smartcontractor.service;

import com.smartcontractor.model.Company;
import com.smartcontractor.model.Employee;
import com.smartcontractor.model.PaymentDetails;
import com.smartcontractor.repository.CompanyRepository;
import com.smartcontractor.repository.EmployeeRepository;
import com.smartcontractor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Employee createEmployee(String userId, String companyId, Employee employee) {
        // Validate User exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        // Validate Company exists
        Optional<Company> companyOpt = companyRepository.findById(companyId);
        if (companyOpt.isEmpty()) {
            throw new RuntimeException("Company not found");
        }

        // Validate Company belongs to User
        Company company = companyOpt.get();
        if (!userId.equals(company.getUserId())) {
            throw new RuntimeException("Company does not belong to the stipulated User");
        }

        // Generate Employee ID (8 chars alphanumeric)
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(generateEmployeeId());
        }

        // Set Company ID mapping
        employee.setCompanyId(companyId);

        return employeeRepository.save(employee);
    }

    @Override
    public boolean deleteEmployee(String employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public List<Employee> getAllEmployees(String companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new RuntimeException("Company not found");
        }

        List<Company> companyList = companyRepository.findByUserId(companyId);
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < companyList.size(); i++) {
            employeeList = companyList.get(i).getEmployees();
        }

        return employeeList;
    }

    @Override
    public List<Employee> getAllEmployeesByCompany(String companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }

    @Override
    public Employee updateEmployee(String employeeId, Employee employee) {

        // check employee exist or not
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }

        Employee updatedEmployee = employeeRepository.findById(employeeId).get();

        updatedEmployee.setName(employee.getName());
        updatedEmployee.setDesignation(employee.getDesignation());
        updatedEmployee.setDepartment(employee.getDepartment());
        updatedEmployee.setContactNumber(employee.getContactNumber());

        if (employee.getPaymentDetails() != null) {

            PaymentDetails paymentDetails = getPaymentDetails(employee, updatedEmployee);

            updatedEmployee.setPaymentDetails(paymentDetails);
        }


        /*// Validate Company belongs to User
        Company company = companyOpt.get();
        if (!userId.equals(company.getUserId())) {
            throw new RuntimeException("Company does not belong to the stipulated User");
        }

        // Set Company ID mapping
        employee.setCompanyId(companyId);*/

        return employeeRepository.save(updatedEmployee);
    }

    private static PaymentDetails getPaymentDetails(Employee employee, Employee updatedEmployee) {
        PaymentDetails paymentDetails = updatedEmployee.getPaymentDetails();

        paymentDetails.setPaymentCycle(employee.getPaymentDetails().getPaymentCycle());
        paymentDetails.setSalary(employee.getPaymentDetails().getSalary());
        paymentDetails.setCurrency(employee.getPaymentDetails().getCurrency());
        paymentDetails.setBankAccount(employee.getPaymentDetails().getBankAccount());
        paymentDetails.setHalfDaySalary(employee.getPaymentDetails().getHalfDaySalary());
        return paymentDetails;
    }


    private String generateEmployeeId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
