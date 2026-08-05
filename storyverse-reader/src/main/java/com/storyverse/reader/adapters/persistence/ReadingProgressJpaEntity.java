package com.storyverse.reader.adapters.persistence;

import com.storyverse.infrastructure.persistence.AuditableBaseEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reading_progress", indexes = {
        @Index(name = "idx_reading_progress_user_story", columnList = "user_id, story_id", unique = true),
        @Index(name = "idx_reading_progress_user_id", columnList = "user_id")
})
public class ReadingProgressJpaEntity extends AuditableBaseEntity {
    @Id @Column(name = "id") private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(name = "story_id", nullable = false) private UUID storyId;
    @Column(name = "current_chapter_id") private UUID currentChapterId;
    @Column(name = "current_chapter_index") private int currentChapterIndex;
    @Column(name = "progress_percentage") private double progressPercentage;
    @Column(name = "status", nullable = false, length = 20) private String status;
    @Column(name = "total_reading_time_seconds") private long totalReadingTimeSeconds;
    @Column(name = "last_read_at") private Instant lastReadAt;
    @Column(name = "started_at") private Instant startedAt;
    @Column(name = "completed_at") private Instant completedAt;

    @Override public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public UUID getStoryId() { return storyId; }
    public void setStoryId(UUID storyId) { this.storyId = storyId; }
    public UUID getCurrentChapterId() { return currentChapterId; }
    public void setCurrentChapterId(UUID cid) { this.currentChapterId = cid; }
    public int getCurrentChapterIndex() { return currentChapterIndex; }
    public void setCurrentChapterIndex(int i) { this.currentChapterIndex = i; }
    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double p) { this.progressPercentage = p; }
    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }
    public long getTotalReadingTimeSeconds() { return totalReadingTimeSeconds; }
    public void setTotalReadingTimeSeconds(long t) { this.totalReadingTimeSeconds = t; }
    public Instant getLastReadAt() { return lastReadAt; }
    public void setLastReadAt(Instant i) { this.lastReadAt = i; }
    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant i) { this.startedAt = i; }
    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant i) { this.completedAt = i; }
}
