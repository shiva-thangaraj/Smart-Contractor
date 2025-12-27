package com.smartcontractor.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PaymentDetails {
    private Double salary;
    private String currency;
    private String paymentCycle;
    private String bankAccount;
    private Double halfDaySalary;
}
