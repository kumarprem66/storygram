package com.storyverse.story.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.AuthorizationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import com.storyverse.story.domain.Chapter;
import com.storyverse.story.domain.Story;
import com.storyverse.story.domain.event.ChapterUpdatedEvent;
import com.storyverse.story.ports.ChapterRepository;
import com.storyverse.story.ports.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateChapterUseCase {

    private final StoryRepository storyRepository;
    private final ChapterRepository chapterRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public UpdateChapterUseCase(StoryRepository storyRepository, ChapterRepository chapterRepository,
                                 EventPublisher eventPublisher, SecurityContext securityContext) {
        this.storyRepository = storyRepository;
        this.chapterRepository = chapterRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID storyId, UUID chapterId, String title, String content) {
        UUID userId = securityContext.requireUserId();
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story", storyId));

        if (!story.isOwnedBy(userId)) {
            throw new AuthorizationException("You do not have permission to edit this chapter");
        }

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter", chapterId));

        chapter.updateContent(title, content);
        chapterRepository.save(chapter);

        // Recalculate story word count
        var chapters = chapterRepository.findByStoryIdOrderByOrderIndex(storyId);
        story.recalculateWordCount(chapters);
        storyRepository.save(story);

        eventPublisher.publish(new ChapterUpdatedEvent(storyId, chapterId,
                EventMetadata.createNew(userId.toString())));
    }
}
