package com.smartcontractor.controller;

import com.smartcontractor.common.ApiResponse;
import com.smartcontractor.model.Attendance;
import com.smartcontractor.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addAttendance(
            @RequestParam String userId,
            @RequestParam String companyId,
            @RequestParam String employeeId,
            @RequestBody Attendance attendance) {
        try {
            Attendance addedAttendance = attendanceService.addAttendance(userId, companyId, employeeId, attendance);
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "Attendance added successfully", addedAttendance), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("User not found".equals(e.getMessage()) || 
                "Company not found".equals(e.getMessage()) || 
                "Employee not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Attendance addition failed", e.getMessage()), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Attendance addition failed", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<?>> deleteAttendance(@RequestParam String attendanceId) {
        boolean isDeleted = attendanceService.deleteAttendance(attendanceId);
        if (isDeleted) {
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Attendance deleted successfully", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Attendance deletion failed", "Attendance not found"), HttpStatus.NOT_FOUND);
    }


    /*public ResponseEntity<ApiResponse<?>> getAllAttendanceById(@RequestParam String employeeId) {


    }*/

    @GetMapping("get/allAttendence")
    public ResponseEntity<ApiResponse<?>> getAttendanceByEmployee(@RequestParam String employeeId) {

        try {
            List<Attendance> attendanceList = attendanceService.getAttendanceByEmployeeId(employeeId);

            return ResponseEntity.ok(
                    ApiResponse.success(
                            HttpStatus.OK.value(),
                            "Attendance fetched successfully",
                            attendanceList
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(
                            HttpStatus.NOT_FOUND.value(),
                            "Attendance not found",
                            e.getMessage()
                    ));
        }
    }
}


