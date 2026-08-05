package com.storyverse.billing.ports;

import java.math.BigDecimal;

/**
 * Port for payment gateway integration. Production implements Stripe/Razorpay.
 */
public interface PaymentGatewayPort {
    PaymentGatewayResult charge(String userId, BigDecimal amount, String currency, String paymentMethod);
    PaymentGatewayResult refund(String transactionId, BigDecimal amount);
}
