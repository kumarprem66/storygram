package com.storyverse.shared.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Base interface for all domain events in StoryVerse.
 * Domain events represent completed business facts that have already occurred.
 * They are immutable and versioned.
 */
public interface DomainEvent {

    /**
     * Unique identifier for this event instance.
     */
    UUID getEventId();

    /**
     * Type name of the event (e.g., "UserRegistered", "StoryPublished").
     */
    String getEventType();

    /**
     * Timestamp when the event occurred.
     */
    Instant getOccurredAt();

    /**
     * Version of the event schema for backward compatibility.
     */
    int getEventVersion();

    /**
     * ID of the aggregate that produced this event.
     */
    UUID getAggregateId();

    /**
     * Type of the aggregate that produced this event.
     */
    String getAggregateType();
}
