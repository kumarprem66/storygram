package com.storyverse.moderation.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class ModerationDecisionMadeEvent extends BaseDomainEvent {
    private final String action;
    private final UUID moderatorId;

    public ModerationDecisionMadeEvent(UUID caseId, String action, UUID moderatorId, EventMetadata metadata) {
        super(caseId, metadata);
        this.action = action; this.moderatorId = moderatorId;
    }

    @Override public String getEventType() { return "ModerationDecisionMade"; }
    @Override public String getAggregateType() { return "ModerationCase"; }
    public String getAction() { return action; }
    public UUID getModeratorId() { return moderatorId; }
}
