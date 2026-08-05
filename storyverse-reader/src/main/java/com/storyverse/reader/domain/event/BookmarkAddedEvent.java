package com.storyverse.reader.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class BookmarkAddedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final UUID storyId;

    public BookmarkAddedEvent(UUID bookmarkId, UUID userId, UUID storyId, EventMetadata metadata) {
        super(bookmarkId, metadata);
        this.userId = userId;
        this.storyId = storyId;
    }

    @Override public String getEventType() { return "BookmarkAdded"; }
    @Override public String getAggregateType() { return "Bookmark"; }
    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
}
