package com.storyverse.reader.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class ReadingStartedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final UUID storyId;

    public ReadingStartedEvent(UUID progressId, UUID userId, UUID storyId, EventMetadata metadata) {
        super(progressId, metadata);
        this.userId = userId;
        this.storyId = storyId;
    }

    @Override public String getEventType() { return "ReadingStarted"; }
    @Override public String getAggregateType() { return "ReadingProgress"; }
    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
}
