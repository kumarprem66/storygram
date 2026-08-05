package com.storyverse.creator.api.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CreatorProfileResponse(
        UUID id, UUID userId, String displayName, String biography,
        String avatarUrl, String bannerUrl, String website,
        Set<String> genres, Set<String> penNames, String language,
        String status, String verificationStatus,
        int followerCount, int storyCount,
        Instant createdAt, Instant updatedAt
) {}
