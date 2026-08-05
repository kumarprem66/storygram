package com.storyverse.reader.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.reader.domain.ReadingProgress;
import com.storyverse.reader.ports.ReadingProgressRepository;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class StartReadingUseCase {
    private final ReadingProgressRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public StartReadingUseCase(ReadingProgressRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(UUID storyId, UUID firstChapterId) {
        UUID userId = securityContext.requireUserId();
        // Check if already started
        var existing = repository.findByUserIdAndStoryId(userId, storyId);
        if (existing.isPresent()) {
            return existing.get().getId();
        }
        ReadingProgress progress = ReadingProgress.start(UUID.randomUUID(), userId, storyId, firstChapterId);
        repository.save(progress);
        eventPublisher.publishAll(progress.getDomainEvents());
        progress.clearDomainEvents();
        return progress.getId();
    }
}
