package com.shrivatsa.Razorpay.Payment.processor.strategy;

import com.shrivatsa.Razorpay.Payment.processor.PaymentProcessor;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorRequest;
import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorResponse;
import com.shrivatsa.Razorpay.common.util.RandomizerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class NetBankingPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

        final String BANK_CODE_FAIL = "BANK_CODE_FAIL";

        String bankCode = request.methodDetails() != null ?
                request.methodDetails().get("bank").toString() : null;

        // simulation
        if (BANK_CODE_FAIL.equals(bankCode)) {
            return new PaymentProcessorResponse.Failure("BANK_REJECTED",
                    "Banked rejected the transaction registration"
            );
        }

        String processorRef = "NBK_PROCESSOR_"+ RandomizerUtil.randomBase64(16);


        return new PaymentProcessorResponse.Pending(processorRef);
    }
}
