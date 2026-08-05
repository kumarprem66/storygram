package com.storyverse.reader.adapters.persistence;

import com.storyverse.reader.domain.ReadingProgress;
import com.storyverse.reader.domain.vo.ReadingStatus;

final class ReadingProgressMapper {
    private ReadingProgressMapper() {}

    static ReadingProgress toDomain(ReadingProgressJpaEntity e) {
        return ReadingProgress.reconstitute(e.getId(), e.getUserId(), e.getStoryId(), e.getCurrentChapterId(),
                e.getCurrentChapterIndex(), e.getProgressPercentage(),
                ReadingStatus.valueOf(e.getStatus()), e.getTotalReadingTimeSeconds(),
                e.getLastReadAt(), e.getStartedAt(), e.getCompletedAt(), e.getCreatedAt(), e.getUpdatedAt());
    }

    static ReadingProgressJpaEntity toEntity(ReadingProgress p) {
        ReadingProgressJpaEntity e = new ReadingProgressJpaEntity();
        e.setId(p.getId()); e.setUserId(p.getUserId()); e.setStoryId(p.getStoryId());
        e.setCurrentChapterId(p.getCurrentChapterId()); e.setCurrentChapterIndex(p.getCurrentChapterIndex());
        e.setProgressPercentage(p.getProgressPercentage()); e.setStatus(p.getStatus().name());
        e.setTotalReadingTimeSeconds(p.getTotalReadingTimeSeconds()); e.setLastReadAt(p.getLastReadAt());
        e.setStartedAt(p.getStartedAt()); e.setCompletedAt(p.getCompletedAt());
        return e;
    }
}
