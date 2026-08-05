package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class StoryPublishedEvent extends BaseDomainEvent {

    private final UUID authorId;
    private final String title;

    public StoryPublishedEvent(UUID storyId, UUID authorId, String title, EventMetadata metadata) {
        super(storyId, metadata);
        this.authorId = authorId;
        this.title = title;
    }

    @Override public String getEventType() { return "StoryPublished"; }
    @Override public String getAggregateType() { return "Story"; }
    public UUID getAuthorId() { return authorId; }
    public String getTitle() { return title; }
}
