package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class StoryDeletedEvent extends BaseDomainEvent {

    private final UUID authorId;

    public StoryDeletedEvent(UUID storyId, UUID authorId, EventMetadata metadata) {
        super(storyId, metadata);
        this.authorId = authorId;
    }

    @Override public String getEventType() { return "StoryDeleted"; }
    @Override public String getAggregateType() { return "Story"; }
    public UUID getAuthorId() { return authorId; }
}
