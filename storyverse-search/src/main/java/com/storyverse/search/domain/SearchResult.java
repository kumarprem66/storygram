package com.storyverse.search.domain;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record SearchResult(
        UUID id,
        String type,
        String title,
        String description,
        String authorName,
        UUID authorId,
        String genre,
        Set<String> tags,
        String language,
        double rating,
        int readCount,
        String coverImageUrl,
        Instant publishedAt,
        String highlightedTitle,
        String highlightedDescription
) {}
