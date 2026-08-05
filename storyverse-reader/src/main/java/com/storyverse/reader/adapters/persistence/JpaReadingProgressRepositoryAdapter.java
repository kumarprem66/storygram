package com.storyverse.reader.adapters.persistence;

import com.storyverse.reader.domain.ReadingProgress;
import com.storyverse.reader.ports.ReadingProgressRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class JpaReadingProgressRepositoryAdapter implements ReadingProgressRepository {
    private final SpringDataReadingProgressRepository springDataRepository;

    public JpaReadingProgressRepositoryAdapter(SpringDataReadingProgressRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override public Optional<ReadingProgress> findById(UUID id) { return springDataRepository.findById(id).map(ReadingProgressMapper::toDomain); }
    @Override public Optional<ReadingProgress> findByUserIdAndStoryId(UUID userId, UUID storyId) { return springDataRepository.findByUserIdAndStoryId(userId, storyId).map(ReadingProgressMapper::toDomain); }
    @Override public List<ReadingProgress> findByUserId(UUID userId) { return springDataRepository.findByUserId(userId).stream().map(ReadingProgressMapper::toDomain).toList(); }
    @Override public ReadingProgress save(ReadingProgress progress) { return ReadingProgressMapper.toDomain(springDataRepository.save(ReadingProgressMapper.toEntity(progress))); }
    @Override public void delete(UUID id) { springDataRepository.deleteById(id); }
}
