package com.shrivatsa.Razorpay.Payment.repository;

import com.shrivatsa.Razorpay.Payment.entity.OrderRecord;
import com.shrivatsa.Razorpay.Payment.entity.Payment;
import com.shrivatsa.Razorpay.common.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOrder_Id(OrderRecord order);

    Optional<Payment> findByIdAndMerchantId(UUID paymentId, UUID merchantId);

    List<Payment> findByStatusAndCreatedAtBefore(PaymentStatus paymentStatus, LocalDateTime globalWindow);
}
