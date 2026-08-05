package com.storyverse.community.adapters;

import com.storyverse.community.domain.Rating;
import com.storyverse.community.ports.RatingRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRatingRepository implements RatingRepository {
    private final Map<UUID, Rating> store = new ConcurrentHashMap<>();

    @Override public Optional<Rating> findByUserIdAndStoryId(UUID userId, UUID storyId) {
        return store.values().stream().filter(r -> r.getUserId().equals(userId) && r.getStoryId().equals(storyId)).findFirst();
    }
    @Override public List<Rating> findByStoryId(UUID storyId) { return store.values().stream().filter(r -> r.getStoryId().equals(storyId)).toList(); }
    @Override public double getAverageRatingByStoryId(UUID storyId) {
        return store.values().stream().filter(r -> r.getStoryId().equals(storyId)).mapToInt(Rating::getScore).average().orElse(0.0);
    }
    @Override public Rating save(Rating rating) { store.put(rating.getId(), rating); return rating; }
    @Override public void delete(UUID id) { store.remove(id); }
}
