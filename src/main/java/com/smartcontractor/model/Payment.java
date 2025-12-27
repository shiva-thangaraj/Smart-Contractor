package com.smartcontractor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    private String paymentId;
    private String employeeId; // Foreign key to Employee
    private Double amount;
    private String date;
    private String time;
    private String note;
}
