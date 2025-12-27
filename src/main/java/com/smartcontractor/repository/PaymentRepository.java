package com.smartcontractor.repository;

import com.smartcontractor.model.Attendance;
import com.smartcontractor.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByEmployeeId(String employeeId);
}
