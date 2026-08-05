package com.storyverse.search.adapters;

import com.storyverse.search.ports.SearchMetadataRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySearchMetadataRepository implements SearchMetadataRepository {
    private final Map<UUID, Instant> store = new ConcurrentHashMap<>();

    @Override
    public void recordIndexed(UUID documentId, String documentType, Instant indexedAt) {
        store.put(documentId, indexedAt);
    }

    @Override
    public Optional<Instant> getLastIndexedAt(UUID documentId) {
        return Optional.ofNullable(store.get(documentId));
    }
}
