package com.storyverse.story.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class ChapterCreatedEvent extends BaseDomainEvent {

    private final UUID chapterId;
    private final String chapterTitle;

    public ChapterCreatedEvent(UUID storyId, UUID chapterId, String chapterTitle, EventMetadata metadata) {
        super(storyId, metadata);
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
    }

    @Override public String getEventType() { return "ChapterCreated"; }
    @Override public String getAggregateType() { return "Story"; }
    public UUID getChapterId() { return chapterId; }
    public String getChapterTitle() { return chapterTitle; }
}
