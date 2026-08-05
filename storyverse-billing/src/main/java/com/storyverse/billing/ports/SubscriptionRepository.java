package com.storyverse.billing.ports;

import com.storyverse.billing.domain.Subscription;
import java.util.*;

public interface SubscriptionRepository {
    Optional<Subscription> findById(UUID id);
    Optional<Subscription> findActiveByUserId(UUID userId);
    Subscription save(Subscription subscription);
}
