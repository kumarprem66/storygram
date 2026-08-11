package com.storyverse.story.adapters.persistence;

import com.storyverse.shared.api.PageRequest;
import com.storyverse.story.domain.Story;
import com.storyverse.story.domain.vo.StoryStatus;
import com.storyverse.story.domain.vo.Visibility;
import com.storyverse.story.ports.StoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaStoryRepositoryAdapter implements StoryRepository {

    private final SpringDataStoryRepository springDataRepository;

    public JpaStoryRepositoryAdapter(SpringDataStoryRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<Story> findById(UUID id) {
        return springDataRepository.findById(id).map(StoryMapper::toDomain);
    }

    @Override
    public List<Story> findByAuthorId(UUID authorId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.page(), pageRequest.size());
        return springDataRepository.findByAuthorId(authorId, pageable)
                .getContent().stream().map(StoryMapper::toDomain).toList();
    }

    @Override
    public List<Story> findByStatusAndVisibility(StoryStatus status, Visibility visibility, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.page(), pageRequest.size());
        return springDataRepository.findByStatusAndVisibility(status.name(), visibility.name(), pageable)
                .getContent().stream().map(StoryMapper::toDomain).toList();
    }

    @Override
    public long countByAuthorId(UUID authorId) {
        return springDataRepository.countByAuthorId(authorId);
    }

    @Override
    public boolean existsById(UUID id) {
        return springDataRepository.existsById(id);
    }

    @Override
    public Story save(Story story) {
        Optional<StoryJpaEntity> existing = springDataRepository.findById(story.getId());

        StoryJpaEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setAuthorId(story.getAuthorId());
            entity.setTitle(story.getTitle());
            entity.setSubtitle(story.getSubtitle());
            entity.setDescription(story.getDescription());
            entity.setGenre(story.getGenre() != null ? story.getGenre().name() : null);
            entity.setTags(new HashSet<>(story.getTags()));
            entity.setLanguage(story.getLanguage());
            entity.setAgeRating(story.getAgeRating() != null ? story.getAgeRating().name() : null);
            entity.setVisibility(story.getVisibility() != null ? story.getVisibility().name() : null);
            entity.setStatus(story.getStatus() != null ? story.getStatus().name() : null);
            entity.setCoverImageUrl(story.getCoverImageUrl());
            entity.setTotalWordCount(story.getTotalWordCount().getValue());
            entity.setPublishedAt(story.getPublishedAt());
        } else {
            entity = StoryMapper.toEntity(story);
        }

        StoryJpaEntity saved = springDataRepository.save(entity);
        return StoryMapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }
}
