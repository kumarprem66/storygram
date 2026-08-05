package com.storyverse.story.api.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record StoryResponse(
        UUID id,
        UUID authorId,
        String title,
        String subtitle,
        String description,
        String genre,
        Set<String> tags,
        String language,
        String ageRating,
        String visibility,
        String status,
        String coverImageUrl,
        int wordCount,
        int estimatedReadingTimeMinutes,
        Instant publishedAt,
        Instant createdAt,
        Instant updatedAt
) {}
