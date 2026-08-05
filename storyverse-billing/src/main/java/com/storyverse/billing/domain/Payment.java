package com.storyverse.billing.domain;

import com.storyverse.billing.domain.vo.PaymentStatus;
import com.storyverse.shared.domain.BaseEntity;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment extends BaseEntity {
    private UUID userId;
    private UUID subscriptionId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String providerTransactionId;
    private String paymentMethod;
    private Instant createdAt;

    private Payment(UUID id) { super(id); }

    public static Payment create(UUID id, UUID userId, UUID subscriptionId, BigDecimal amount, String currency, String paymentMethod) {
        Payment p = new Payment(id);
        p.userId = userId; p.subscriptionId = subscriptionId; p.amount = amount;
        p.currency = currency; p.status = PaymentStatus.PENDING; p.paymentMethod = paymentMethod;
        p.createdAt = Instant.now();
        return p;
    }

    public static Payment reconstitute(UUID id, UUID userId, UUID subscriptionId, BigDecimal amount, String currency,
                                        PaymentStatus status, String providerTransactionId, String paymentMethod, Instant createdAt) {
        Payment p = new Payment(id);
        p.userId = userId; p.subscriptionId = subscriptionId; p.amount = amount; p.currency = currency;
        p.status = status; p.providerTransactionId = providerTransactionId; p.paymentMethod = paymentMethod; p.createdAt = createdAt;
        return p;
    }

    public void complete(String providerTransactionId) { this.status = PaymentStatus.COMPLETED; this.providerTransactionId = providerTransactionId; }
    public void fail() { this.status = PaymentStatus.FAILED; }
    public void refund() { this.status = PaymentStatus.REFUNDED; }

    public UUID getUserId() { return userId; }
    public UUID getSubscriptionId() { return subscriptionId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public PaymentStatus getStatus() { return status; }
    public String getProviderTransactionId() { return providerTransactionId; }
    public String getPaymentMethod() { return paymentMethod; }
    public Instant getCreatedAt() { return createdAt; }
}
