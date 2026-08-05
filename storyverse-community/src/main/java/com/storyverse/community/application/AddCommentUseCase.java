package com.storyverse.community.application;

import com.storyverse.community.domain.Comment;
import com.storyverse.community.domain.event.CommentCreatedEvent;
import com.storyverse.community.ports.CommentRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class AddCommentUseCase {
    private final CommentRepository commentRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public AddCommentUseCase(CommentRepository commentRepository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.commentRepository = commentRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(UUID storyId, UUID parentCommentId, String content) {
        UUID userId = securityContext.requireUserId();
        Comment comment = Comment.create(UUID.randomUUID(), userId, storyId, parentCommentId, content);
        commentRepository.save(comment);
        eventPublisher.publish(new CommentCreatedEvent(comment.getId(), userId, storyId, EventMetadata.createNew(userId.toString())));
        return comment.getId();
    }
}
