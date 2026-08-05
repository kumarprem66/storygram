package com.storyverse.moderation.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class ModerationCaseCreatedEvent extends BaseDomainEvent {
    private final UUID reporterId;
    private final String targetType;
    private final String reason;

    public ModerationCaseCreatedEvent(UUID caseId, UUID reporterId, String targetType, String reason, EventMetadata metadata) {
        super(caseId, metadata);
        this.reporterId = reporterId; this.targetType = targetType; this.reason = reason;
    }

    @Override public String getEventType() { return "ModerationCaseCreated"; }
    @Override public String getAggregateType() { return "ModerationCase"; }
    public UUID getReporterId() { return reporterId; }
    public String getTargetType() { return targetType; }
    public String getReason() { return reason; }
}
