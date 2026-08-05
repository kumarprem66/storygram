package com.storyverse.reader.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.reader.domain.Bookmark;
import com.storyverse.reader.domain.event.BookmarkAddedEvent;
import com.storyverse.reader.ports.BookmarkRepository;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class AddBookmarkUseCase {
    private final BookmarkRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public AddBookmarkUseCase(BookmarkRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(UUID storyId, UUID chapterId, String note) {
        UUID userId = securityContext.requireUserId();
        Bookmark bookmark = Bookmark.create(UUID.randomUUID(), userId, storyId, chapterId, note);
        repository.save(bookmark);
        eventPublisher.publish(new BookmarkAddedEvent(bookmark.getId(), userId, storyId, EventMetadata.createNew(userId.toString())));
        return bookmark.getId();
    }
}
