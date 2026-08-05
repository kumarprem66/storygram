package com.storyverse.shared.domain.event;

/**
 * Marker interface for integration events.
 * Integration events cross bounded context boundaries and are published
 * through the Transactional Outbox pattern via Kafka.
 *
 * Unlike domain events (which are internal to a bounded context),
 * integration events form the public API contract between modules.
 */
public interface IntegrationEvent extends DomainEvent {
}
