package com.storyverse.billing.domain;

import com.storyverse.billing.domain.vo.*;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class Subscription extends BaseEntity {
    private UUID userId;
    private SubscriptionPlan plan;
    private SubscriptionStatus status;
    private Instant startDate;
    private Instant endDate;
    private Instant cancelledAt;
    private boolean autoRenew;
    private Instant createdAt;
    private Instant updatedAt;

    private Subscription(UUID id) { super(id); }

    public static Subscription create(UUID id, UUID userId, SubscriptionPlan plan) {
        Subscription s = new Subscription(id);
        s.userId = userId;
        s.plan = plan;
        s.status = SubscriptionStatus.ACTIVE;
        s.startDate = Instant.now();
        s.endDate = Instant.now().plusSeconds(30L * 24 * 3600);
        s.autoRenew = true;
        s.createdAt = Instant.now();
        s.updatedAt = Instant.now();
        return s;
    }

    public static Subscription reconstitute(UUID id, UUID userId, SubscriptionPlan plan, SubscriptionStatus status,
                                             Instant startDate, Instant endDate, Instant cancelledAt,
                                             boolean autoRenew, Instant createdAt, Instant updatedAt) {
        Subscription s = new Subscription(id);
        s.userId = userId; s.plan = plan; s.status = status; s.startDate = startDate;
        s.endDate = endDate; s.cancelledAt = cancelledAt; s.autoRenew = autoRenew;
        s.createdAt = createdAt; s.updatedAt = updatedAt;
        return s;
    }

    public void cancel() { this.status = SubscriptionStatus.CANCELLED; this.cancelledAt = Instant.now(); this.autoRenew = false; this.updatedAt = Instant.now(); }
    public void renew() { this.startDate = Instant.now(); this.endDate = Instant.now().plusSeconds(30L * 24 * 3600); this.status = SubscriptionStatus.ACTIVE; this.updatedAt = Instant.now(); }
    public void upgrade(SubscriptionPlan newPlan) { this.plan = newPlan; this.updatedAt = Instant.now(); }
    public void expire() { this.status = SubscriptionStatus.EXPIRED; this.updatedAt = Instant.now(); }
    public boolean isActive() { return status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.TRIALING; }

    public UUID getUserId() { return userId; }
    public SubscriptionPlan getPlan() { return plan; }
    public SubscriptionStatus getStatus() { return status; }
    public Instant getStartDate() { return startDate; }
    public Instant getEndDate() { return endDate; }
    public Instant getCancelledAt() { return cancelledAt; }
    public boolean isAutoRenew() { return autoRenew; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
