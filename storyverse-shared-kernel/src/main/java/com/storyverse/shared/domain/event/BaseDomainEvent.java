package com.storyverse.shared.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Abstract base implementation of {@link DomainEvent}.
 * Provides common fields and construction logic for all domain events.
 */
public abstract class BaseDomainEvent implements DomainEvent {

    private final UUID eventId;
    private final Instant occurredAt;
    private final UUID aggregateId;
    private final EventMetadata metadata;

    protected BaseDomainEvent(UUID aggregateId, EventMetadata metadata) {
        this.eventId = metadata.eventId();
        this.occurredAt = metadata.occurredAt();
        this.aggregateId = aggregateId;
        this.metadata = metadata;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    @Override
    public int getEventVersion() {
        return 1;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }

    public String getCorrelationId() {
        return metadata.correlationId();
    }

    public String getCausationId() {
        return metadata.causationId();
    }
}
