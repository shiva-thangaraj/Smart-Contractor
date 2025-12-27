package com.smartcontractor.service;

import com.smartcontractor.model.Payment;

public interface PaymentService {
    Payment addPayment(String userId, String companyId, String employeeId, Payment payment);
    boolean deletePayment(String paymentId);
}
