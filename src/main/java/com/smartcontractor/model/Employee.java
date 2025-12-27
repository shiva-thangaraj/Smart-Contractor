package com.smartcontractor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private String employeeId;
    private String companyId; // Foreign key to Company
    private String name;
    private String designation;
    private String department;
    private String contactNumber;
    
    @Embedded
    private PaymentDetails paymentDetails;

    @OneToMany
    @JoinColumn(name = "employeeId", insertable = false, updatable = false)
    private List<Attendance> attendance;

    @OneToMany
    @JoinColumn(name = "employeeId", insertable = false, updatable = false)
    private List<Payment> payments;
}
