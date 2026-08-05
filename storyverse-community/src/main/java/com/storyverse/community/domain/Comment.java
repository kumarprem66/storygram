package com.storyverse.community.domain;

import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class Comment extends BaseEntity {
    private UUID userId;
    private UUID storyId;
    private UUID parentCommentId;
    private String content;
    private boolean deleted;
    private Instant createdAt;
    private Instant updatedAt;

    private Comment(UUID id) { super(id); }

    public static Comment create(UUID id, UUID userId, UUID storyId, UUID parentCommentId, String content) {
        Comment c = new Comment(id);
        c.userId = userId;
        c.storyId = storyId;
        c.parentCommentId = parentCommentId;
        c.content = content;
        c.deleted = false;
        c.createdAt = Instant.now();
        c.updatedAt = Instant.now();
        return c;
    }

    public static Comment reconstitute(UUID id, UUID userId, UUID storyId, UUID parentCommentId,
                                        String content, boolean deleted, Instant createdAt, Instant updatedAt) {
        Comment c = new Comment(id);
        c.userId = userId;
        c.storyId = storyId;
        c.parentCommentId = parentCommentId;
        c.content = content;
        c.deleted = deleted;
        c.createdAt = createdAt;
        c.updatedAt = updatedAt;
        return c;
    }

    public void edit(String newContent) { this.content = newContent; this.updatedAt = Instant.now(); }
    public void softDelete() { this.deleted = true; this.content = "[deleted]"; this.updatedAt = Instant.now(); }
    public boolean isReply() { return parentCommentId != null; }
    public boolean isOwnedBy(UUID userId) { return this.userId.equals(userId); }

    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
    public UUID getParentCommentId() { return parentCommentId; }
    public String getContent() { return content; }
    public boolean isDeleted() { return deleted; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
