package com.storyverse.creator.application;

import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.creator.ports.CreatorProfileRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.ConflictException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class BecomeCreatorUseCase {
    private final CreatorProfileRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public BecomeCreatorUseCase(CreatorProfileRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(String displayName) {
        UUID userId = securityContext.requireUserId();
        if (repository.existsByUserId(userId)) {
            throw new ConflictException("CREATOR_EXISTS", "User already has a creator profile");
        }
        CreatorProfile profile = CreatorProfile.create(UUID.randomUUID(), userId, displayName);
        repository.save(profile);
        eventPublisher.publishAll(profile.getDomainEvents());
        profile.clearDomainEvents();
        return profile.getId();
    }
}
