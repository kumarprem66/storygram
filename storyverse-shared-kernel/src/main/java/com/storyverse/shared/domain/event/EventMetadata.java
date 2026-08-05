package com.storyverse.shared.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Metadata attached to every domain event for tracing and auditing.
 */
public record EventMetadata(
        UUID eventId,
        String correlationId,
        String causationId,
        Instant occurredAt,
        String triggeredBy
) {

    /**
     * Creates metadata with a new event ID and the current timestamp.
     */
    public static EventMetadata create(String correlationId, String causationId, String triggeredBy) {
        return new EventMetadata(
                UUID.randomUUID(),
                correlationId,
                causationId,
                Instant.now(),
                triggeredBy
        );
    }

    /**
     * Creates metadata with auto-generated correlation and causation IDs.
     */
    public static EventMetadata createNew(String triggeredBy) {
        String correlationId = UUID.randomUUID().toString();
        return new EventMetadata(
                UUID.randomUUID(),
                correlationId,
                correlationId,
                Instant.now(),
                triggeredBy
        );
    }
}
