package com.storyverse.billing.adapters;

import com.storyverse.billing.ports.PaymentGatewayPort;
import com.storyverse.billing.ports.PaymentGatewayResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Stub payment gateway for dev/test. Always succeeds.
 * Production replaces with Stripe/Razorpay adapter.
 */
@Component
public class StubPaymentGatewayAdapter implements PaymentGatewayPort {
    private static final Logger log = LoggerFactory.getLogger(StubPaymentGatewayAdapter.class);

    @Override
    public PaymentGatewayResult charge(String userId, BigDecimal amount, String currency, String paymentMethod) {
        log.info("[PAYMENT STUB] Charging user={} amount={} {} method={}", userId, amount, currency, paymentMethod);
        return PaymentGatewayResult.success("txn_" + UUID.randomUUID().toString().substring(0, 8));
    }

    @Override
    public PaymentGatewayResult refund(String transactionId, BigDecimal amount) {
        log.info("[PAYMENT STUB] Refunding txn={} amount={}", transactionId, amount);
        return PaymentGatewayResult.success("ref_" + UUID.randomUUID().toString().substring(0, 8));
    }
}
