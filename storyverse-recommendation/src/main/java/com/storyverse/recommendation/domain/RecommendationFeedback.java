package com.storyverse.recommendation.domain;

import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class RecommendationFeedback extends BaseEntity {
    private UUID userId;
    private UUID storyId;
    private String feedbackType;
    private Instant createdAt;

    private RecommendationFeedback(UUID id) { super(id); }

    public static RecommendationFeedback create(UUID id, UUID userId, UUID storyId, String feedbackType) {
        RecommendationFeedback f = new RecommendationFeedback(id);
        f.userId = userId;
        f.storyId = storyId;
        f.feedbackType = feedbackType;
        f.createdAt = Instant.now();
        return f;
    }

    public static RecommendationFeedback reconstitute(UUID id, UUID userId, UUID storyId, String feedbackType, Instant createdAt) {
        RecommendationFeedback f = new RecommendationFeedback(id);
        f.userId = userId;
        f.storyId = storyId;
        f.feedbackType = feedbackType;
        f.createdAt = createdAt;
        return f;
    }

    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
    public String getFeedbackType() { return feedbackType; }
    public Instant getCreatedAt() { return createdAt; }
}
