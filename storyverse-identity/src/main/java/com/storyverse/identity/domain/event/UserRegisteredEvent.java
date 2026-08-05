package com.storyverse.identity.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class UserRegisteredEvent extends BaseDomainEvent {

    private final String email;
    private final String username;

    public UserRegisteredEvent(UUID userId, String email, String username, EventMetadata metadata) {
        super(userId, metadata);
        this.email = email;
        this.username = username;
    }

    @Override
    public String getEventType() {
        return "UserRegistered";
    }

    @Override
    public String getAggregateType() {
        return "User";
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }
}
