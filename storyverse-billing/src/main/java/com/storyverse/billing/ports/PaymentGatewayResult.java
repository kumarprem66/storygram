package com.storyverse.billing.ports;

public record PaymentGatewayResult(
        boolean success,
        String transactionId,
        String errorMessage
) {
    public static PaymentGatewayResult success(String transactionId) { return new PaymentGatewayResult(true, transactionId, null); }
    public static PaymentGatewayResult failure(String error) { return new PaymentGatewayResult(false, null, error); }
}
