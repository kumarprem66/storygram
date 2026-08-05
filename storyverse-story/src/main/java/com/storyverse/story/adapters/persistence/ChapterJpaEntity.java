package com.storyverse.story.adapters.persistence;

import com.storyverse.infrastructure.persistence.AuditableBaseEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "chapters", indexes = {
        @Index(name = "idx_chapters_story_id", columnList = "story_id"),
        @Index(name = "idx_chapters_story_order", columnList = "story_id, order_index")
})
public class ChapterJpaEntity extends AuditableBaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "story_id", nullable = false, updatable = false)
    private UUID storyId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "word_count", nullable = false)
    private int wordCount;

    @Override
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getStoryId() { return storyId; }
    public void setStoryId(UUID storyId) { this.storyId = storyId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getWordCount() { return wordCount; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }
}
