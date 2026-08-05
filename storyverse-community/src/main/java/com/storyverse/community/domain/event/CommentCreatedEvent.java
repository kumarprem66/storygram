package com.storyverse.community.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class CommentCreatedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final UUID storyId;

    public CommentCreatedEvent(UUID commentId, UUID userId, UUID storyId, EventMetadata metadata) {
        super(commentId, metadata);
        this.userId = userId;
        this.storyId = storyId;
    }

    @Override public String getEventType() { return "CommentCreated"; }
    @Override public String getAggregateType() { return "Comment"; }
    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
}
