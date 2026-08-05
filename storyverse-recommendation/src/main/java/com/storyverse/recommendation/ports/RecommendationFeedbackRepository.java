package com.storyverse.recommendation.ports;

import com.storyverse.recommendation.domain.RecommendationFeedback;
import java.util.List;
import java.util.UUID;

public interface RecommendationFeedbackRepository {
    RecommendationFeedback save(RecommendationFeedback feedback);
    List<RecommendationFeedback> findByUserId(UUID userId);
}
