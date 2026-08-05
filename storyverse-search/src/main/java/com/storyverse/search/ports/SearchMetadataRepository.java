package com.storyverse.search.ports;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Port for search metadata persistence (tracking index state).
 */
public interface SearchMetadataRepository {
    void recordIndexed(UUID documentId, String documentType, Instant indexedAt);
    Optional<Instant> getLastIndexedAt(UUID documentId);
}
