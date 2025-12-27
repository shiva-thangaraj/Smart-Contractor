package com.smartcontractor.service;

import com.smartcontractor.model.Attendance;
import com.smartcontractor.model.Company;

import java.util.List;

public interface AttendanceService {
    Attendance addAttendance(String userId, String companyId, String employeeId, Attendance attendance);
    boolean deleteAttendance(String attendanceId);
    List<Attendance> getAttendanceByEmployeeId(String employeeId);
}
