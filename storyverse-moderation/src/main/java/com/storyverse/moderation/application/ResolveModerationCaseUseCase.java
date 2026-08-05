package com.storyverse.moderation.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.moderation.domain.ModerationCase;
import com.storyverse.moderation.domain.event.ModerationDecisionMadeEvent;
import com.storyverse.moderation.domain.vo.EnforcementAction;
import com.storyverse.moderation.ports.ModerationCaseRepository;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ResolveModerationCaseUseCase {
    private final ModerationCaseRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public ResolveModerationCaseUseCase(ModerationCaseRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository; this.eventPublisher = eventPublisher; this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID caseId, String action, String notes) {
        UUID moderatorId = securityContext.requireUserId();
        ModerationCase moderationCase = repository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("ModerationCase", caseId));
        EnforcementAction enforcementAction = EnforcementAction.valueOf(action.toUpperCase());
        moderationCase.resolve(enforcementAction, notes);
        repository.save(moderationCase);
        eventPublisher.publish(new ModerationDecisionMadeEvent(caseId, action, moderatorId, EventMetadata.createNew(moderatorId.toString())));
    }
}
