package com.storyverse.billing.adapters;

import com.storyverse.billing.domain.Subscription;
import com.storyverse.billing.domain.vo.SubscriptionStatus;
import com.storyverse.billing.ports.SubscriptionRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySubscriptionRepository implements SubscriptionRepository {
    private final Map<UUID, Subscription> store = new ConcurrentHashMap<>();

    @Override public Optional<Subscription> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public Optional<Subscription> findActiveByUserId(UUID userId) {
        return store.values().stream().filter(s -> s.getUserId().equals(userId) && s.isActive()).findFirst();
    }
    @Override public Subscription save(Subscription subscription) { store.put(subscription.getId(), subscription); return subscription; }
}
