package com.shrivatsa.Razorpay.Payment.gateway;

import com.shrivatsa.Razorpay.Payment.configuration.PaymentAdapterConfig;
import com.shrivatsa.Razorpay.Payment.gateway.adapters.CardPaymentAdapter;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentRequest;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentResult;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {
    private final Map<PaymentMethod,PaymentAdapter> paymentAdapterMap;
    public PaymentResult initiate(PaymentRequest request){
        PaymentAdapter adapter = paymentAdapterMap.get(request.method());
        if(adapter == null){
            throw new IllegalArgumentException("No payment adapter found for method " + request.method());
        }
        return adapter.initiate(request);

    }
}
