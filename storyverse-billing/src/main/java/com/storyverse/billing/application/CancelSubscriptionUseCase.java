package com.storyverse.billing.application;

import com.storyverse.billing.domain.Subscription;
import com.storyverse.billing.ports.SubscriptionRepository;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class CancelSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final SecurityContext securityContext;

    public CancelSubscriptionUseCase(SubscriptionRepository subscriptionRepository, SecurityContext securityContext) {
        this.subscriptionRepository = subscriptionRepository;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute() {
        UUID userId = securityContext.requireUserId();
        Subscription subscription = subscriptionRepository.findActiveByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", userId));
        subscription.cancel();
        subscriptionRepository.save(subscription);
    }
}
