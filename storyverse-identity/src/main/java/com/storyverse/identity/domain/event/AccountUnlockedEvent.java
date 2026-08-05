package com.storyverse.identity.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class AccountUnlockedEvent extends BaseDomainEvent {

    public AccountUnlockedEvent(UUID userId, EventMetadata metadata) {
        super(userId, metadata);
    }

    @Override
    public String getEventType() {
        return "AccountUnlocked";
    }

    @Override
    public String getAggregateType() {
        return "User";
    }
}
