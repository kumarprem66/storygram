package com.storyverse.recommendation.ports;

import com.storyverse.recommendation.domain.RecommendationItem;
import com.storyverse.recommendation.domain.vo.RecommendationType;
import java.util.List;
import java.util.UUID;

/**
 * Port for recommendation generation logic.
 * Implementations provide different strategies (popularity, content-based, AI-assisted).
 */
public interface RecommendationEngine {
    List<RecommendationItem> getPersonalized(UUID userId, int limit);
    List<RecommendationItem> getTrending(String timeWindow, int limit);
    List<RecommendationItem> getSimilar(UUID storyId, int limit);
    List<RecommendationItem> getByGenre(String genre, int limit);
    List<RecommendationItem> getNewReleases(int limit);
}
