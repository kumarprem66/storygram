package com.storyverse.moderation.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.moderation.domain.ModerationCase;
import com.storyverse.moderation.domain.event.ModerationCaseCreatedEvent;
import com.storyverse.moderation.domain.vo.ReportReason;
import com.storyverse.moderation.ports.ModerationCaseRepository;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ReportContentUseCase {
    private final ModerationCaseRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public ReportContentUseCase(ModerationCaseRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository; this.eventPublisher = eventPublisher; this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(UUID targetId, String targetType, String reason, String description) {
        UUID reporterId = securityContext.requireUserId();
        ReportReason reportReason = ReportReason.valueOf(reason.toUpperCase());
        ModerationCase moderationCase = ModerationCase.create(UUID.randomUUID(), reporterId, targetId, targetType, reportReason, description);
        repository.save(moderationCase);
        eventPublisher.publish(new ModerationCaseCreatedEvent(moderationCase.getId(), reporterId, targetType, reason, EventMetadata.createNew(reporterId.toString())));
        return moderationCase.getId();
    }
}
