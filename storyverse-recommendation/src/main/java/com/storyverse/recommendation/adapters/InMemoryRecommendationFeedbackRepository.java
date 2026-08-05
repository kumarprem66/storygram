package com.storyverse.recommendation.adapters;

import com.storyverse.recommendation.domain.RecommendationFeedback;
import com.storyverse.recommendation.ports.RecommendationFeedbackRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRecommendationFeedbackRepository implements RecommendationFeedbackRepository {
    private final Map<UUID, RecommendationFeedback> store = new ConcurrentHashMap<>();

    @Override
    public RecommendationFeedback save(RecommendationFeedback feedback) {
        store.put(feedback.getId(), feedback);
        return feedback;
    }

    @Override
    public List<RecommendationFeedback> findByUserId(UUID userId) {
        return store.values().stream().filter(f -> f.getUserId().equals(userId)).toList();
    }
}
