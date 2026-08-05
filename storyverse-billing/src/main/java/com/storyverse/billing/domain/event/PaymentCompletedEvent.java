package com.storyverse.billing.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentCompletedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final BigDecimal amount;

    public PaymentCompletedEvent(UUID paymentId, UUID userId, BigDecimal amount, EventMetadata metadata) {
        super(paymentId, metadata);
        this.userId = userId; this.amount = amount;
    }

    @Override public String getEventType() { return "PaymentCompleted"; }
    @Override public String getAggregateType() { return "Payment"; }
    public UUID getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
}
