package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class ChapterUpdatedEvent extends BaseDomainEvent {

    private final UUID chapterId;

    public ChapterUpdatedEvent(UUID storyId, UUID chapterId, EventMetadata metadata) {
        super(storyId, metadata);
        this.chapterId = chapterId;
    }

    @Override public String getEventType() { return "ChapterUpdated"; }
    @Override public String getAggregateType() { return "Story"; }
    public UUID getChapterId() { return chapterId; }
}
