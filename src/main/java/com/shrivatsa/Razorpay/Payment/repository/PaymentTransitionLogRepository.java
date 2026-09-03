package com.shrivatsa.Razorpay.Payment.repository;
import com.shrivatsa.Razorpay.Payment.entity.PaymentTransitionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTransitionLogRepository extends JpaRepository<PaymentTransitionLog, UUID> {
}
