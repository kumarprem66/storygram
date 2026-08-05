package com.storyverse.story.adapters.persistence;

import com.storyverse.story.domain.Chapter;
import com.storyverse.story.ports.ChapterRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaChapterRepositoryAdapter implements ChapterRepository {

    private final SpringDataChapterRepository springDataRepository;

    public JpaChapterRepositoryAdapter(SpringDataChapterRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<Chapter> findById(UUID id) {
        return springDataRepository.findById(id).map(ChapterMapper::toDomain);
    }

    @Override
    public List<Chapter> findByStoryIdOrderByOrderIndex(UUID storyId) {
        return springDataRepository.findByStoryIdOrderByOrderIndex(storyId)
                .stream().map(ChapterMapper::toDomain).toList();
    }

    @Override
    public int countByStoryId(UUID storyId) {
        return springDataRepository.countByStoryId(storyId);
    }

    @Override
    public Chapter save(Chapter chapter) {
        ChapterJpaEntity entity = ChapterMapper.toEntity(chapter);
        ChapterJpaEntity saved = springDataRepository.save(entity);
        return ChapterMapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public void deleteAllByStoryId(UUID storyId) {
        springDataRepository.deleteAllByStoryId(storyId);
    }
}
