package com.shrivatsa.Razorpay.Payment.processor;

import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorRequest;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    public PaymentProcessorResponse charge(PaymentProcessorRequest paymentProcessorRequest);
}
