package com.shrivatsa.Razorpay.Payment.stateMachine;


import com.shrivatsa.Razorpay.Payment.entity.Payment;
import com.shrivatsa.Razorpay.Payment.entity.PaymentTransitionLog;
import com.shrivatsa.Razorpay.Payment.repository.PaymentTransitionLogRepository;
import com.shrivatsa.Razorpay.common.enums.PaymentActor;
import com.shrivatsa.Razorpay.common.enums.PaymentEvent;
import com.shrivatsa.Razorpay.common.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentTransitionService {

    private final PaymentTransitionLogRepository paymentTransitionLogRepository;
    private final PaymentStateMachine paymentStateMachine;

    public PaymentStatus apply(Payment payment, PaymentEvent event) {
        PaymentStatus next = paymentStateMachine.transition(payment.getStatus(), event);

        PaymentTransitionLog log = PaymentTransitionLog.builder()
                .payment(payment)
                .fromStatus(payment.getStatus())
                .event(event)
                .toStatus(next)
                .actor(PaymentActor.SYSTEM) //TODO: fetch merchant context to identify actor
                .occurredAt(LocalDateTime.now())
                .build();

        payment.setStatus(next);

        paymentTransitionLogRepository.save(log);
        return next;
    }
}
