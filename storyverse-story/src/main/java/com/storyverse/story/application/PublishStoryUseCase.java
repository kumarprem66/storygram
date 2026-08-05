package com.storyverse.story.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.AuthorizationException;
import com.storyverse.shared.domain.exception.BusinessRuleViolationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import com.storyverse.story.domain.Story;
import com.storyverse.story.ports.ChapterRepository;
import com.storyverse.story.ports.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PublishStoryUseCase {

    private final StoryRepository storyRepository;
    private final ChapterRepository chapterRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public PublishStoryUseCase(StoryRepository storyRepository, ChapterRepository chapterRepository,
                               EventPublisher eventPublisher, SecurityContext securityContext) {
        this.storyRepository = storyRepository;
        this.chapterRepository = chapterRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID storyId) {
        UUID userId = securityContext.requireUserId();
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story", storyId));

        if (!story.isOwnedBy(userId)) {
            throw new AuthorizationException("You do not have permission to publish this story");
        }

        int chapterCount = chapterRepository.countByStoryId(storyId);
        if (chapterCount == 0) {
            throw new BusinessRuleViolationException("STORY_NO_CHAPTERS", "Story must have at least one chapter to be published");
        }

        story.publish();
        storyRepository.save(story);
        eventPublisher.publishAll(story.getDomainEvents());
        story.clearDomainEvents();
    }
}
