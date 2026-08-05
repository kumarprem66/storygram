package com.storyverse.identity.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class UserVerifiedEvent extends BaseDomainEvent {

    private final String email;

    public UserVerifiedEvent(UUID userId, String email, EventMetadata metadata) {
        super(userId, metadata);
        this.email = email;
    }

    @Override
    public String getEventType() {
        return "UserVerified";
    }

    @Override
    public String getAggregateType() {
        return "User";
    }

    public String getEmail() { return email; }
}
