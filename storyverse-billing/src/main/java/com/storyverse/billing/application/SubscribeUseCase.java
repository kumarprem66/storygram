package com.storyverse.billing.application;

import com.storyverse.billing.domain.Subscription;
import com.storyverse.billing.domain.event.SubscriptionActivatedEvent;
import com.storyverse.billing.domain.vo.SubscriptionPlan;
import com.storyverse.billing.ports.SubscriptionRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.ConflictException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class SubscribeUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public SubscribeUseCase(SubscriptionRepository subscriptionRepository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.subscriptionRepository = subscriptionRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(String plan) {
        UUID userId = securityContext.requireUserId();
        if (subscriptionRepository.findActiveByUserId(userId).isPresent()) {
            throw new ConflictException("ACTIVE_SUBSCRIPTION_EXISTS", "User already has an active subscription");
        }
        SubscriptionPlan subscriptionPlan = SubscriptionPlan.valueOf(plan.toUpperCase());
        Subscription subscription = Subscription.create(UUID.randomUUID(), userId, subscriptionPlan);
        subscriptionRepository.save(subscription);
        eventPublisher.publish(new SubscriptionActivatedEvent(subscription.getId(), userId, plan, EventMetadata.createNew(userId.toString())));
        return subscription.getId();
    }
}
