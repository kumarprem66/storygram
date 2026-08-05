package com.storyverse.creator.application;

import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.creator.ports.CreatorProfileRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.AuthorizationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class UpdateCreatorProfileUseCase {
    private final CreatorProfileRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public UpdateCreatorProfileUseCase(CreatorProfileRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID creatorId, String displayName, String biography, String website,
                        String language, Set<String> genres, Set<String> penNames) {
        UUID userId = securityContext.requireUserId();
        CreatorProfile profile = repository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("CreatorProfile", creatorId));
        if (!profile.isOwnedBy(userId)) {
            throw new AuthorizationException("You do not have permission to edit this profile");
        }
        profile.updateProfile(displayName, biography, website, language, genres, penNames);
        repository.save(profile);
        eventPublisher.publishAll(profile.getDomainEvents());
        profile.clearDomainEvents();
    }
}
