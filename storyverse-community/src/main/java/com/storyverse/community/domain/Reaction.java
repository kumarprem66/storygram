package com.storyverse.community.domain;

import com.storyverse.community.domain.vo.ReactionType;
import com.storyverse.community.domain.vo.TargetType;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class Reaction extends BaseEntity {
    private UUID userId;
    private UUID targetId;
    private TargetType targetType;
    private ReactionType reactionType;
    private Instant createdAt;

    private Reaction(UUID id) { super(id); }

    public static Reaction create(UUID id, UUID userId, UUID targetId, TargetType targetType, ReactionType reactionType) {
        Reaction r = new Reaction(id);
        r.userId = userId;
        r.targetId = targetId;
        r.targetType = targetType;
        r.reactionType = reactionType;
        r.createdAt = Instant.now();
        return r;
    }

    public static Reaction reconstitute(UUID id, UUID userId, UUID targetId, TargetType targetType,
                                         ReactionType reactionType, Instant createdAt) {
        Reaction r = new Reaction(id);
        r.userId = userId;
        r.targetId = targetId;
        r.targetType = targetType;
        r.reactionType = reactionType;
        r.createdAt = createdAt;
        return r;
    }

    public UUID getUserId() { return userId; }
    public UUID getTargetId() { return targetId; }
    public TargetType getTargetType() { return targetType; }
    public ReactionType getReactionType() { return reactionType; }
    public Instant getCreatedAt() { return createdAt; }
}
