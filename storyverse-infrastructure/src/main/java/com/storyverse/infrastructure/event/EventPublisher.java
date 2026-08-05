package com.storyverse.infrastructure.event;

import com.storyverse.shared.domain.event.DomainEvent;

/**
 * Port interface for publishing domain events.
 * Business modules use this to publish events without knowing the underlying infrastructure.
 * Implementations may use Spring ApplicationEventPublisher (in-process) or Kafka (distributed).
 */
public interface EventPublisher {

    /**
     * Publishes a single domain event.
     */
    void publish(DomainEvent event);

    /**
     * Publishes multiple domain events in order.
     */
    default void publishAll(Iterable<? extends DomainEvent> events) {
        events.forEach(this::publish);
    }
}
