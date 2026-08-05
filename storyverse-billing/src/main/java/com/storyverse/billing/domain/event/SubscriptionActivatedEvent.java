package com.storyverse.billing.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class SubscriptionActivatedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final String plan;

    public SubscriptionActivatedEvent(UUID subscriptionId, UUID userId, String plan, EventMetadata metadata) {
        super(subscriptionId, metadata);
        this.userId = userId; this.plan = plan;
    }

    @Override public String getEventType() { return "SubscriptionActivated"; }
    @Override public String getAggregateType() { return "Subscription"; }
    public UUID getUserId() { return userId; }
    public String getPlan() { return plan; }
}
