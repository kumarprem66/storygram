package com.storyverse.reader.domain;

import com.storyverse.reader.domain.event.*;
import com.storyverse.reader.domain.vo.ReadingStatus;
import com.storyverse.shared.domain.BaseAggregateRoot;
import com.storyverse.shared.domain.event.EventMetadata;

import java.time.Instant;
import java.util.UUID;

public class ReadingProgress extends BaseAggregateRoot {

    private UUID userId;
    private UUID storyId;
    private UUID currentChapterId;
    private int currentChapterIndex;
    private double progressPercentage;
    private ReadingStatus status;
    private long totalReadingTimeSeconds;
    private Instant lastReadAt;
    private Instant startedAt;
    private Instant completedAt;
    private Instant createdAt;
    private Instant updatedAt;

    private ReadingProgress(UUID id) {
        super(id);
    }

    public static ReadingProgress start(UUID id, UUID userId, UUID storyId, UUID firstChapterId) {
        ReadingProgress rp = new ReadingProgress(id);
        rp.userId = userId;
        rp.storyId = storyId;
        rp.currentChapterId = firstChapterId;
        rp.currentChapterIndex = 0;
        rp.progressPercentage = 0.0;
        rp.status = ReadingStatus.IN_PROGRESS;
        rp.totalReadingTimeSeconds = 0;
        rp.lastReadAt = Instant.now();
        rp.startedAt = Instant.now();
        rp.createdAt = Instant.now();
        rp.updatedAt = Instant.now();

        rp.registerEvent(new ReadingStartedEvent(id, userId, storyId, EventMetadata.createNew(userId.toString())));
        return rp;
    }

    public static ReadingProgress reconstitute(UUID id, UUID userId, UUID storyId, UUID currentChapterId,
                                               int currentChapterIndex, double progressPercentage,
                                               ReadingStatus status, long totalReadingTimeSeconds,
                                               Instant lastReadAt, Instant startedAt, Instant completedAt,
                                               Instant createdAt, Instant updatedAt) {
        ReadingProgress rp = new ReadingProgress(id);
        rp.userId = userId;
        rp.storyId = storyId;
        rp.currentChapterId = currentChapterId;
        rp.currentChapterIndex = currentChapterIndex;
        rp.progressPercentage = progressPercentage;
        rp.status = status;
        rp.totalReadingTimeSeconds = totalReadingTimeSeconds;
        rp.lastReadAt = lastReadAt;
        rp.startedAt = startedAt;
        rp.completedAt = completedAt;
        rp.createdAt = createdAt;
        rp.updatedAt = updatedAt;
        return rp;
    }

    public void updateProgress(UUID chapterId, int chapterIndex, double percentage, long sessionSeconds) {
        this.currentChapterId = chapterId;
        this.currentChapterIndex = chapterIndex;
        this.progressPercentage = percentage;
        this.totalReadingTimeSeconds += sessionSeconds;
        this.lastReadAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.status == ReadingStatus.NOT_STARTED) {
            this.status = ReadingStatus.IN_PROGRESS;
        }
    }

    public void completeStory() {
        this.status = ReadingStatus.COMPLETED;
        this.progressPercentage = 100.0;
        this.completedAt = Instant.now();
        this.updatedAt = Instant.now();
        registerEvent(new StoryCompletedEvent(getId(), userId, storyId, EventMetadata.createNew(userId.toString())));
    }

    public void abandon() {
        this.status = ReadingStatus.ABANDONED;
        this.updatedAt = Instant.now();
    }

    public boolean isOwnedBy(UUID userId) { return this.userId.equals(userId); }

    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
    public UUID getCurrentChapterId() { return currentChapterId; }
    public int getCurrentChapterIndex() { return currentChapterIndex; }
    public double getProgressPercentage() { return progressPercentage; }
    public ReadingStatus getStatus() { return status; }
    public long getTotalReadingTimeSeconds() { return totalReadingTimeSeconds; }
    public Instant getLastReadAt() { return lastReadAt; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
