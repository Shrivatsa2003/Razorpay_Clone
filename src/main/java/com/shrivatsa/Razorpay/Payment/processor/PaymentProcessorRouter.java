package com.shrivatsa.Razorpay.Payment.processor;

import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorRequest;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorResponse;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentProcessorRouter {

    private final Map<PaymentMethod, PaymentProcessor> paymentProcessors;

    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        PaymentProcessor processor = paymentProcessors.get(request.method());
        if (processor == null) {
            throw new IllegalArgumentException("No payment processor registered for method: "+request.method());
        }
        return processor.charge(request);
    }
}
