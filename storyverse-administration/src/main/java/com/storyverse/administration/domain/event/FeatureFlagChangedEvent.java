package com.storyverse.administration.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class FeatureFlagChangedEvent extends BaseDomainEvent {
    private final String flagName;
    private final String newStatus;

    public FeatureFlagChangedEvent(UUID flagId, String flagName, String newStatus, EventMetadata metadata) {
        super(flagId, metadata);
        this.flagName = flagName; this.newStatus = newStatus;
    }

    @Override public String getEventType() { return "FeatureFlagChanged"; }
    @Override public String getAggregateType() { return "FeatureFlag"; }
    public String getFlagName() { return flagName; }
    public String getNewStatus() { return newStatus; }
}
