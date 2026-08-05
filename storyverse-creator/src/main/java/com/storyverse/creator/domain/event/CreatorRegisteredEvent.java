package com.storyverse.creator.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class CreatorRegisteredEvent extends BaseDomainEvent {
    private final UUID userId;
    private final String displayName;

    public CreatorRegisteredEvent(UUID creatorId, UUID userId, String displayName, EventMetadata metadata) {
        super(creatorId, metadata);
        this.userId = userId;
        this.displayName = displayName;
    }

    @Override public String getEventType() { return "CreatorRegistered"; }
    @Override public String getAggregateType() { return "CreatorProfile"; }
    public UUID getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
}
