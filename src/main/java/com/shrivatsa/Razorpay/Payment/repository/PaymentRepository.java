package com.shrivatsa.Razorpay.Payment.repository;

import com.shrivatsa.Razorpay.Payment.entity.OrderRecord;
import com.shrivatsa.Razorpay.Payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOrder_Id(OrderRecord order);
}
