package com.storyverse.community.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class ReactionAddedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final UUID targetId;
    private final String reactionType;

    public ReactionAddedEvent(UUID reactionId, UUID userId, UUID targetId, String reactionType, EventMetadata metadata) {
        super(reactionId, metadata);
        this.userId = userId;
        this.targetId = targetId;
        this.reactionType = reactionType;
    }

    @Override public String getEventType() { return "ReactionAdded"; }
    @Override public String getAggregateType() { return "Reaction"; }
    public UUID getUserId() { return userId; }
    public UUID getTargetId() { return targetId; }
    public String getReactionType() { return reactionType; }
}
