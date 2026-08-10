package com.shrivatsa.Razorpay.Payment.configuration;

import com.shrivatsa.Razorpay.Payment.gateway.PaymentAdapter;
import com.shrivatsa.Razorpay.Payment.gateway.adapters.CardPaymentAdapter;
import com.shrivatsa.Razorpay.Payment.gateway.adapters.NetBankingAdapter;
import com.shrivatsa.Razorpay.Payment.gateway.adapters.UpiPaymentAdapter;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentAdapterConfig {
    private final NetBankingAdapter netBankingAdapter;
    private final UpiPaymentAdapter upiPaymentAdapter;
    private final CardPaymentAdapter cardPaymentAdapter;
    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap(){
        return Map.of(
                PaymentMethod.CARD,cardPaymentAdapter,
                PaymentMethod.UPI, upiPaymentAdapter,
                PaymentMethod.NETBANKING,netBankingAdapter
        );
    }

}
