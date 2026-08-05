package com.storyverse.community.domain;

import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class Rating extends BaseEntity {
    private UUID userId;
    private UUID storyId;
    private int score;
    private Instant createdAt;
    private Instant updatedAt;

    private Rating(UUID id) { super(id); }

    public static Rating create(UUID id, UUID userId, UUID storyId, int score) {
        if (score < 1 || score > 5) throw new IllegalArgumentException("Rating must be between 1 and 5");
        Rating r = new Rating(id);
        r.userId = userId;
        r.storyId = storyId;
        r.score = score;
        r.createdAt = Instant.now();
        r.updatedAt = Instant.now();
        return r;
    }

    public static Rating reconstitute(UUID id, UUID userId, UUID storyId, int score, Instant createdAt, Instant updatedAt) {
        Rating r = new Rating(id);
        r.userId = userId;
        r.storyId = storyId;
        r.score = score;
        r.createdAt = createdAt;
        r.updatedAt = updatedAt;
        return r;
    }

    public void updateScore(int newScore) {
        if (newScore < 1 || newScore > 5) throw new IllegalArgumentException("Rating must be between 1 and 5");
        this.score = newScore;
        this.updatedAt = Instant.now();
    }

    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
    public int getScore() { return score; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
