package com.shrivatsa.Razorpay.Payment.gateway.adapters;

import com.shrivatsa.Razorpay.Payment.gateway.PaymentAdapter;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentRequest;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentResult;
import com.shrivatsa.Razorpay.Payment.processor.PaymentProcessor;
import com.shrivatsa.Razorpay.Payment.processor.PaymentProcessorRouter;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorRequest;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorResponse;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class NetBankingAdapter implements PaymentAdapter {
    private final PaymentProcessorRouter paymentProcessorRouter;

    @Override
    public PaymentResult initiate(PaymentRequest request) {
        log.info("initiate payment for the payment method"+request.method());
        try {
            PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
                    request.paymentId(),
                    PaymentMethod.NETBANKING,
                    request.amount(),
                    request.methodDetails()
            );

            PaymentProcessorResponse paymentProcessorResponse =
                    paymentProcessorRouter.charge(paymentProcessorRequest);

            return switch (paymentProcessorResponse) {
                case PaymentProcessorResponse.Failure failure ->
                        new PaymentResult.Failure(failure.errorCode(), failure.errorDescription());

                case PaymentProcessorResponse.Pending pending ->
                        new PaymentResult.Pending(pending.processorReference());

                case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankReference());

            };
        } catch(Exception e) {
            log.warn("NetBanking failed, paymentId: {}", request.paymentId());
            return new PaymentResult.Failure("NBK_FAILED", e.getMessage());
        }

    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return new PaymentResult.Success("NBK_REF");
    }
}
