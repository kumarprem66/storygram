package com.storyverse.reader.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class StoryCompletedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final UUID storyId;

    public StoryCompletedEvent(UUID progressId, UUID userId, UUID storyId, EventMetadata metadata) {
        super(progressId, metadata);
        this.userId = userId;
        this.storyId = storyId;
    }

    @Override public String getEventType() { return "StoryCompleted"; }
    @Override public String getAggregateType() { return "ReadingProgress"; }
    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
}
