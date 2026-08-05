package com.storyverse.identity.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;

import java.util.UUID;

public class RoleAssignedEvent extends BaseDomainEvent {

    private final String role;

    public RoleAssignedEvent(UUID userId, String role, EventMetadata metadata) {
        super(userId, metadata);
        this.role = role;
    }

    @Override
    public String getEventType() {
        return "RoleAssigned";
    }

    @Override
    public String getAggregateType() {
        return "User";
    }

    public String getRole() { return role; }
}
