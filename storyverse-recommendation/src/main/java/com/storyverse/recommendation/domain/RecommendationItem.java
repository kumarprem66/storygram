package com.storyverse.recommendation.domain;

import com.storyverse.recommendation.domain.vo.RecommendationType;
import java.util.UUID;

public record RecommendationItem(
        UUID storyId,
        String title,
        String authorName,
        UUID authorId,
        String genre,
        String coverImageUrl,
        double score,
        String reason,
        RecommendationType type
) {}
