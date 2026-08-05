package com.storyverse.reader.adapters.persistence;

import com.storyverse.reader.domain.Bookmark;
import com.storyverse.reader.ports.BookmarkRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryBookmarkRepository implements BookmarkRepository {
    private final Map<UUID, Bookmark> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Bookmark> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Bookmark> findByUserId(UUID userId) {
        return store.values().stream().filter(b -> b.getUserId().equals(userId)).toList();
    }

    @Override
    public List<Bookmark> findByUserIdAndStoryId(UUID userId, UUID storyId) {
        return store.values().stream()
                .filter(b -> b.getUserId().equals(userId) && b.getStoryId().equals(storyId)).toList();
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        store.put(bookmark.getId(), bookmark);
        return bookmark;
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }
}
