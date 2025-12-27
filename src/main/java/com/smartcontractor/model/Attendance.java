package com.smartcontractor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    private String attendanceId;
    private String employeeId; // Foreign key to Employee
    private String date;
    private String status;
    private String dayType;
}
