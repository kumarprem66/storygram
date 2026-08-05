package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class ChapterDeletedEvent extends BaseDomainEvent {

    private final UUID chapterId;

    public ChapterDeletedEvent(UUID storyId, UUID chapterId, EventMetadata metadata) {
        super(storyId, metadata);
        this.chapterId = chapterId;
    }

    @Override public String getEventType() { return "ChapterDeleted"; }
    @Override public String getAggregateType() { return "Story"; }
    public UUID getChapterId() { return chapterId; }
}
