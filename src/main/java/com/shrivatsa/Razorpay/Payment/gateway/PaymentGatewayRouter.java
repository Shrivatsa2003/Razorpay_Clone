package com.shrivatsa.Razorpay.Payment.gateway;

import com.shrivatsa.Razorpay.Payment.configuration.PaymentAdapterConfig;
import com.shrivatsa.Razorpay.Payment.gateway.adapters.CardPaymentAdapter;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentRequest;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentResult;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {

    private final Map<PaymentMethod,PaymentAdapter> paymentAdapters;

    public PaymentResult initiate(PaymentRequest request){
        PaymentAdapter adapter = paymentAdapters.get(request.method());
        if(adapter == null){
            throw new IllegalArgumentException("No payment adapter found for method " + request.method());
        }
        return adapter.initiate(request);

    }

    public PaymentResult capture(PaymentMethod method, UUID paymentId) {
        PaymentAdapter adapter = paymentAdapters.get(method);
        if (adapter == null) {
            throw new IllegalArgumentException("No payment adapter registered for method: "+method);
        }
        return adapter.capture(paymentId);
    }
}
