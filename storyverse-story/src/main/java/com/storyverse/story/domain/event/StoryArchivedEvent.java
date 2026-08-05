package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class StoryArchivedEvent extends BaseDomainEvent {

    public StoryArchivedEvent(UUID storyId, EventMetadata metadata) {
        super(storyId, metadata);
    }

    @Override public String getEventType() { return "StoryArchived"; }
    @Override public String getAggregateType() { return "Story"; }
}
