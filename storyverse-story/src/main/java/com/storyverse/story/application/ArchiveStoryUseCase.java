package com.storyverse.story.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.AuthorizationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import com.storyverse.story.domain.Story;
import com.storyverse.story.ports.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ArchiveStoryUseCase {

    private final StoryRepository storyRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public ArchiveStoryUseCase(StoryRepository storyRepository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.storyRepository = storyRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID storyId) {
        UUID userId = securityContext.requireUserId();
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story", storyId));

        if (!story.isOwnedBy(userId)) {
            throw new AuthorizationException("You do not have permission to archive this story");
        }

        story.archive();
        storyRepository.save(story);
        eventPublisher.publishAll(story.getDomainEvents());
        story.clearDomainEvents();
    }
}
