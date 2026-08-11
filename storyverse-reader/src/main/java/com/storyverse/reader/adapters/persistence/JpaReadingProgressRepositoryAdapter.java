package com.storyverse.reader.adapters.persistence;

import com.storyverse.reader.domain.ReadingProgress;
import com.storyverse.reader.ports.ReadingProgressRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaReadingProgressRepositoryAdapter implements ReadingProgressRepository {
    private final SpringDataReadingProgressRepository springDataRepository;

    public JpaReadingProgressRepositoryAdapter(SpringDataReadingProgressRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<ReadingProgress> findById(UUID id) {
        return springDataRepository.findById(id).map(ReadingProgressMapper::toDomain);
    }

    @Override
    public Optional<ReadingProgress> findByUserIdAndStoryId(UUID userId, UUID storyId) {
        return springDataRepository.findByUserIdAndStoryId(userId, storyId).map(ReadingProgressMapper::toDomain);
    }

    @Override
    public List<ReadingProgress> findByUserId(UUID userId) {
        return springDataRepository.findByUserId(userId).stream().map(ReadingProgressMapper::toDomain).toList();
    }

    @Override
    public ReadingProgress save(ReadingProgress progress) {
        Optional<ReadingProgressJpaEntity> existing = springDataRepository.findById(progress.getId());

        ReadingProgressJpaEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setCurrentChapterId(progress.getCurrentChapterId());
            entity.setCurrentChapterIndex(progress.getCurrentChapterIndex());
            entity.setProgressPercentage(progress.getProgressPercentage());
            entity.setStatus(progress.getStatus().name());
            entity.setTotalReadingTimeSeconds(progress.getTotalReadingTimeSeconds());
            entity.setLastReadAt(progress.getLastReadAt());
            entity.setStartedAt(progress.getStartedAt());
            entity.setCompletedAt(progress.getCompletedAt());
        } else {
            entity = ReadingProgressMapper.toEntity(progress);
        }

        ReadingProgressJpaEntity saved = springDataRepository.save(entity);
        return ReadingProgressMapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }
}
