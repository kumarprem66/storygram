package com.storyverse.recommendation.application;

import com.storyverse.recommendation.domain.RecommendationItem;
import com.storyverse.recommendation.ports.RecommendationEngine;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class GetSimilarStoriesUseCase {
    private final RecommendationEngine engine;

    public GetSimilarStoriesUseCase(RecommendationEngine engine) { this.engine = engine; }

    public List<RecommendationItem> execute(UUID storyId, int limit) {
        return engine.getSimilar(storyId, Math.min(limit, 20));
    }
}
