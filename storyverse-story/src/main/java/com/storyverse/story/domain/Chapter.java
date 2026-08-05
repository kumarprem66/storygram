package com.storyverse.story.domain;

import com.storyverse.shared.domain.BaseEntity;
import com.storyverse.story.domain.vo.ChapterStatus;
import com.storyverse.story.domain.vo.WordCount;

import java.time.Instant;
import java.util.UUID;

/**
 * Chapter entity belonging to a Story aggregate.
 */
public class Chapter extends BaseEntity {

    private UUID storyId;
    private String title;
    private String content;
    private int orderIndex;
    private ChapterStatus status;
    private WordCount wordCount;
    private Instant createdAt;
    private Instant updatedAt;

    private Chapter(UUID id) {
        super(id);
    }

    public static Chapter create(UUID id, UUID storyId, String title, String content, int orderIndex) {
        Chapter chapter = new Chapter(id);
        chapter.storyId = storyId;
        chapter.title = title;
        chapter.content = content;
        chapter.orderIndex = orderIndex;
        chapter.status = ChapterStatus.DRAFT;
        chapter.wordCount = WordCount.fromText(content);
        chapter.createdAt = Instant.now();
        chapter.updatedAt = Instant.now();
        return chapter;
    }

    public static Chapter reconstitute(UUID id, UUID storyId, String title, String content,
                                        int orderIndex, ChapterStatus status, WordCount wordCount,
                                        Instant createdAt, Instant updatedAt) {
        Chapter chapter = new Chapter(id);
        chapter.storyId = storyId;
        chapter.title = title;
        chapter.content = content;
        chapter.orderIndex = orderIndex;
        chapter.status = status;
        chapter.wordCount = wordCount;
        chapter.createdAt = createdAt;
        chapter.updatedAt = updatedAt;
        return chapter;
    }

    public void updateContent(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
        this.wordCount = WordCount.fromText(newContent);
        this.updatedAt = Instant.now();
    }

    public void publish() {
        this.status = ChapterStatus.PUBLISHED;
        this.updatedAt = Instant.now();
    }

    public void revertToDraft() {
        this.status = ChapterStatus.DRAFT;
        this.updatedAt = Instant.now();
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
        this.updatedAt = Instant.now();
    }

    public boolean isPublished() {
        return status == ChapterStatus.PUBLISHED;
    }

    // Getters
    public UUID getStoryId() { return storyId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getOrderIndex() { return orderIndex; }
    public ChapterStatus getStatus() { return status; }
    public WordCount getWordCount() { return wordCount; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
