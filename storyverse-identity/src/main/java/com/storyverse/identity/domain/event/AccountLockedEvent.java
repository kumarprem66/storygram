package com.storyverse.identity.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class AccountLockedEvent extends BaseDomainEvent {

    public AccountLockedEvent(UUID userId, EventMetadata metadata) {
        super(userId, metadata);
    }

    @Override
    public String getEventType() {
        return "AccountLocked";
    }

    @Override
    public String getAggregateType() {
        return "User";
    }
}
