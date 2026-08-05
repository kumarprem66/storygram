package com.storyverse.story.adapters.persistence;

import com.storyverse.story.domain.Story;
import com.storyverse.story.domain.vo.*;

import java.util.HashSet;

final class StoryMapper {

    private StoryMapper() {}

    static Story toDomain(StoryJpaEntity entity) {
        return Story.reconstitute(
                entity.getId(),
                entity.getAuthorId(),
                entity.getTitle(),
                entity.getSubtitle(),
                entity.getDescription(),
                entity.getGenre() != null ? Genre.valueOf(entity.getGenre()) : null,
                entity.getTags() != null ? new HashSet<>(entity.getTags()) : new HashSet<>(),
                entity.getLanguage(),
                entity.getAgeRating() != null ? AgeRating.valueOf(entity.getAgeRating()) : null,
                entity.getVisibility() != null ? Visibility.valueOf(entity.getVisibility()) : null,
                entity.getStatus() != null ? StoryStatus.valueOf(entity.getStatus()) : null,
                entity.getCoverImageUrl(),
                WordCount.of(entity.getTotalWordCount()),
                entity.getPublishedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    static StoryJpaEntity toEntity(Story story) {
        StoryJpaEntity entity = new StoryJpaEntity();
        entity.setId(story.getId());
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
        return entity;
    }
}
