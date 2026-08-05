package com.storyverse.billing.ports;

import com.storyverse.billing.domain.Payment;
import java.util.*;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(UUID id);
    List<Payment> findByUserId(UUID userId);
}
