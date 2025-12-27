package com.smartcontractor.service;

import com.smartcontractor.model.Attendance;
import com.smartcontractor.model.Company;
import com.smartcontractor.model.Employee;
import com.smartcontractor.repository.AttendanceRepository;
import com.smartcontractor.repository.CompanyRepository;
import com.smartcontractor.repository.EmployeeRepository;
import com.smartcontractor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, 
                                 EmployeeRepository employeeRepository,
                                 CompanyRepository companyRepository,
                                 UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Attendance addAttendance(String userId, String companyId, String employeeId, Attendance attendance) {
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

        // Validate Employee belongs to Company
        Employee employee = employeeOpt.get();
        if (!companyId.equals(employee.getCompanyId())) {
            throw new RuntimeException("Employee does not belong to the specified Company");
        }

        // Generate Attendance ID (6 chars alphanumeric)
        if (attendance.getAttendanceId() == null || attendance.getAttendanceId().isEmpty()) {
            attendance.setAttendanceId(generateAttendanceId());
        }

        // Set Employee ID mapping
        attendance.setEmployeeId(employeeId);

        return attendanceRepository.save(attendance);
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

    @Override
    public boolean deleteAttendance(String attendanceId) {
        if (attendanceRepository.existsById(attendanceId)) {
            attendanceRepository.deleteById(attendanceId);
            return true;
        }
        return false;
    }

    @Override
    public List<Attendance> getAttendanceByEmployeeId(String employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Attendance> attendanceList = employee.getAttendance();

        return attendanceList;

    }
}
