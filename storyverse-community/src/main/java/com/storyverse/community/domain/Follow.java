package com.storyverse.community.domain;

import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class Follow extends BaseEntity {
    private UUID followerId;
    private UUID followingId;
    private Instant createdAt;

    private Follow(UUID id) { super(id); }

    public static Follow create(UUID id, UUID followerId, UUID followingId) {
        Follow f = new Follow(id);
        f.followerId = followerId;
        f.followingId = followingId;
        f.createdAt = Instant.now();
        return f;
    }

    public static Follow reconstitute(UUID id, UUID followerId, UUID followingId, Instant createdAt) {
        Follow f = new Follow(id);
        f.followerId = followerId;
        f.followingId = followingId;
        f.createdAt = createdAt;
        return f;
    }

    public UUID getFollowerId() { return followerId; }
    public UUID getFollowingId() { return followingId; }
    public Instant getCreatedAt() { return createdAt; }
}
