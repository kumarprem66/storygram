package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class StoryUpdatedEvent extends BaseDomainEvent {

    private final String title;

    public StoryUpdatedEvent(UUID storyId, String title, EventMetadata metadata) {
        super(storyId, metadata);
        this.title = title;
    }

    @Override public String getEventType() { return "StoryUpdated"; }
    @Override public String getAggregateType() { return "Story"; }
    public String getTitle() { return title; }
}
