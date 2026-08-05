package com.storyverse.creator.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class CreatorVerifiedEvent extends BaseDomainEvent {
    private final UUID userId;

    public CreatorVerifiedEvent(UUID creatorId, UUID userId, EventMetadata metadata) {
        super(creatorId, metadata);
        this.userId = userId;
    }

    @Override public String getEventType() { return "CreatorVerified"; }
    @Override public String getAggregateType() { return "CreatorProfile"; }
    public UUID getUserId() { return userId; }
}
