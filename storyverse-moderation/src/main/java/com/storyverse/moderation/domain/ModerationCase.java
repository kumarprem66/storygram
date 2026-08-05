package com.storyverse.moderation.domain;

import com.storyverse.moderation.domain.vo.*;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class ModerationCase extends BaseEntity {
    private UUID reporterId;
    private UUID targetId;
    private String targetType;
    private ReportReason reason;
    private String description;
    private ModerationStatus status;
    private EnforcementAction action;
    private UUID assignedModeratorId;
    private String moderatorNotes;
    private Instant createdAt;
    private Instant resolvedAt;

    private ModerationCase(UUID id) { super(id); }

    public static ModerationCase create(UUID id, UUID reporterId, UUID targetId, String targetType, ReportReason reason, String description) {
        ModerationCase c = new ModerationCase(id);
        c.reporterId = reporterId; c.targetId = targetId; c.targetType = targetType;
        c.reason = reason; c.description = description; c.status = ModerationStatus.PENDING;
        c.action = EnforcementAction.NONE; c.createdAt = Instant.now();
        return c;
    }

    public static ModerationCase reconstitute(UUID id, UUID reporterId, UUID targetId, String targetType,
                                               ReportReason reason, String description, ModerationStatus status,
                                               EnforcementAction action, UUID assignedModeratorId,
                                               String moderatorNotes, Instant createdAt, Instant resolvedAt) {
        ModerationCase c = new ModerationCase(id);
        c.reporterId = reporterId; c.targetId = targetId; c.targetType = targetType;
        c.reason = reason; c.description = description; c.status = status; c.action = action;
        c.assignedModeratorId = assignedModeratorId; c.moderatorNotes = moderatorNotes;
        c.createdAt = createdAt; c.resolvedAt = resolvedAt;
        return c;
    }

    public void assign(UUID moderatorId) { this.assignedModeratorId = moderatorId; this.status = ModerationStatus.ASSIGNED; }
    public void resolve(EnforcementAction action, String notes) { this.action = action; this.moderatorNotes = notes; this.status = ModerationStatus.RESOLVED; this.resolvedAt = Instant.now(); }
    public void dismiss(String notes) { this.action = EnforcementAction.NONE; this.moderatorNotes = notes; this.status = ModerationStatus.DISMISSED; this.resolvedAt = Instant.now(); }
    public void escalate() { this.status = ModerationStatus.ESCALATED; }

    public UUID getReporterId() { return reporterId; }
    public UUID getTargetId() { return targetId; }
    public String getTargetType() { return targetType; }
    public ReportReason getReason() { return reason; }
    public String getDescription() { return description; }
    public ModerationStatus getStatus() { return status; }
    public EnforcementAction getAction() { return action; }
    public UUID getAssignedModeratorId() { return assignedModeratorId; }
    public String getModeratorNotes() { return moderatorNotes; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getResolvedAt() { return resolvedAt; }
}
