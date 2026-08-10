package com.shrivatsa.Razorpay.Payment.configuration;

import com.shrivatsa.Razorpay.Payment.processor.PaymentProcessor;
import com.shrivatsa.Razorpay.Payment.processor.strategy.CardPaymentProcessor;
import com.shrivatsa.Razorpay.Payment.processor.strategy.NetBankingPaymentProcessor;
import com.shrivatsa.Razorpay.Payment.processor.strategy.UpiPaymentProcessor;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap() {
        return Map.of(
                PaymentMethod.CARD, new CardPaymentProcessor(),
                PaymentMethod.NETBANKING, new NetBankingPaymentProcessor(),
                PaymentMethod.UPI, new UpiPaymentProcessor()
        );
    }
}
