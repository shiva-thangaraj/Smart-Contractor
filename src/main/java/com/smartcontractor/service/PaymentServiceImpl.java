package com.smartcontractor.service;

import com.smartcontractor.model.Company;
import com.smartcontractor.model.Employee;
import com.smartcontractor.model.Payment;
import com.smartcontractor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, EmployeeRepository employeeRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Payment addPayment(String userId, String companyId, String employeeId, Payment payment) {

        String[] dateAndTime = getCurrentTime().split(";");

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
            throw new RuntimeException("Company does not belong to the specified User");
        }

        // Validate Employee exists
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            throw new RuntimeException("Employee not found");
        }

        if (payment.getDate() == null && payment.getTime() == null) {
            payment.setDate(dateAndTime[0]);
            payment.setTime(dateAndTime[1]);
        }

        // Validate Employee belongs to Company
        Employee employee = employeeOpt.get();
        if (!companyId.equals(employee.getCompanyId())) {
            throw new RuntimeException("Employee does not belong to the specified Company");
        }

        // Generate Attendance ID (6 chars alphanumeric)
        if (payment.getPaymentId() == null || payment.getPaymentId().isEmpty()) {
            payment.setPaymentId(generateAttendanceId());
        }

        // Set Employee ID mapping
        payment.setEmployeeId(employeeId);

        return paymentRepository.save(payment);
    }

    @Override
    public boolean deletePayment(String paymentId) {
        if (paymentRepository.existsById(paymentId)) {
            paymentRepository.deleteById(paymentId);
            return true;
        }
        return false;
    }


    private String generateAttendanceId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String getCurrentTime() {

        LocalDate currentDate = LocalDate.now();
        String date = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // current time
        LocalTime currentTime = LocalTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern("hh:mm a"));

        System.out.println("Date: " + date);
        System.out.println("Time: " + time);

        return date + ";" + time;
    }
}
