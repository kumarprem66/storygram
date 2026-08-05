package com.storyverse.billing.adapters;

import com.storyverse.billing.domain.Payment;
import com.storyverse.billing.ports.PaymentRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryPaymentRepository implements PaymentRepository {
    private final Map<UUID, Payment> store = new ConcurrentHashMap<>();

    @Override public Payment save(Payment payment) { store.put(payment.getId(), payment); return payment; }
    @Override public Optional<Payment> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<Payment> findByUserId(UUID userId) { return store.values().stream().filter(p -> p.getUserId().equals(userId)).toList(); }
}
