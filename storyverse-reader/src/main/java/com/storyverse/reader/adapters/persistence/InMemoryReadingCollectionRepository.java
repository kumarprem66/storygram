package com.storyverse.reader.adapters.persistence;

import com.storyverse.reader.domain.ReadingCollection;
import com.storyverse.reader.ports.ReadingCollectionRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryReadingCollectionRepository implements ReadingCollectionRepository {
    private final Map<UUID, ReadingCollection> store = new ConcurrentHashMap<>();

    @Override
    public Optional<ReadingCollection> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ReadingCollection> findByUserId(UUID userId) {
        return store.values().stream().filter(c -> c.getUserId().equals(userId)).toList();
    }

    @Override
    public ReadingCollection save(ReadingCollection collection) {
        store.put(collection.getId(), collection);
        return collection;
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }
}
