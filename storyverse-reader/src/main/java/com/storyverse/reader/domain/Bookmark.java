package com.storyverse.reader.domain;

import com.storyverse.shared.domain.BaseEntity;

import java.time.Instant;
import java.util.UUID;

public class Bookmark extends BaseEntity {

    private UUID userId;
    private UUID storyId;
    private UUID chapterId;
    private String note;
    private Instant createdAt;

    private Bookmark(UUID id) { super(id); }

    public static Bookmark create(UUID id, UUID userId, UUID storyId, UUID chapterId, String note) {
        Bookmark b = new Bookmark(id);
        b.userId = userId;
        b.storyId = storyId;
        b.chapterId = chapterId;
        b.note = note;
        b.createdAt = Instant.now();
        return b;
    }

    public static Bookmark reconstitute(UUID id, UUID userId, UUID storyId, UUID chapterId, String note, Instant createdAt) {
        Bookmark b = new Bookmark(id);
        b.userId = userId;
        b.storyId = storyId;
        b.chapterId = chapterId;
        b.note = note;
        b.createdAt = createdAt;
        return b;
    }

    public boolean isOwnedBy(UUID userId) { return this.userId.equals(userId); }

    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
    public UUID getChapterId() { return chapterId; }
    public String getNote() { return note; }
    public Instant getCreatedAt() { return createdAt; }
}
