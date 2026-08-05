package com.storyverse.story.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.security.SecurityContext;
import com.storyverse.story.domain.Story;
import com.storyverse.story.domain.vo.Genre;
import com.storyverse.story.ports.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateStoryUseCase {

    private final StoryRepository storyRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public CreateStoryUseCase(StoryRepository storyRepository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.storyRepository = storyRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(String title, String description, Genre genre, String language) {
        UUID authorId = securityContext.requireUserId();
        Story story = Story.create(UUID.randomUUID(), authorId, title, description, genre, language);
        storyRepository.save(story);
        eventPublisher.publishAll(story.getDomainEvents());
        story.clearDomainEvents();
        return story.getId();
    }
}
