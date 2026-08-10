package com.shrivatsa.Razorpay.Payment.processor.strategy;


import com.shrivatsa.Razorpay.Payment.processor.PaymentProcessor;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorRequest;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorResponse;

public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        return null;
    }
}
