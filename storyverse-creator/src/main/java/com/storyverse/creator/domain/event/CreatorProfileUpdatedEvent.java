package com.storyverse.creator.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class CreatorProfileUpdatedEvent extends BaseDomainEvent {

    public CreatorProfileUpdatedEvent(UUID creatorId, EventMetadata metadata) {
        super(creatorId, metadata);
    }

    @Override public String getEventType() { return "CreatorProfileUpdated"; }
    @Override public String getAggregateType() { return "CreatorProfile"; }
}
