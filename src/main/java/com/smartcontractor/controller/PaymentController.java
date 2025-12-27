package com.smartcontractor.controller;

import com.smartcontractor.common.ApiResponse;
import com.smartcontractor.model.Payment;
import com.smartcontractor.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createPayment(
            @RequestParam String userId,
            @RequestParam String companyId,
            @RequestParam String employeeId,
            @RequestBody Payment payment) {
        try {
            Payment addedAttendance = paymentService.addPayment(userId, companyId, employeeId, payment);
            return new ResponseEntity<>(ApiResponse.success(HttpStatus.CREATED.value(), "Payment added successfully", addedAttendance), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("User not found".equals(e.getMessage()) ||
                    "Company not found".equals(e.getMessage()) ||
                    "Employee not found".equals(e.getMessage())) {
                return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payment addition failed", e.getMessage()), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Payment addition failed", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<?>> deletePayment(@RequestParam String paymentId) {
        try {
            if (paymentService.deletePayment(paymentId)){
                return new ResponseEntity<>(ApiResponse.success(HttpStatus.OK.value(), "Payment deleted successfully", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payment deletion failed", "Payment not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Payment deletion failed", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
