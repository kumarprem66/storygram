package com.storyverse.story.adapters.persistence;

import com.storyverse.story.domain.Chapter;
import com.storyverse.story.domain.vo.ChapterStatus;
import com.storyverse.story.domain.vo.WordCount;

final class ChapterMapper {

    private ChapterMapper() {}

    static Chapter toDomain(ChapterJpaEntity entity) {
        return Chapter.reconstitute(
                entity.getId(),
                entity.getStoryId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getOrderIndex(),
                ChapterStatus.valueOf(entity.getStatus()),
                WordCount.of(entity.getWordCount()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    static ChapterJpaEntity toEntity(Chapter chapter) {
        ChapterJpaEntity entity = new ChapterJpaEntity();
        entity.setId(chapter.getId());
        entity.setStoryId(chapter.getStoryId());
        entity.setTitle(chapter.getTitle());
        entity.setContent(chapter.getContent());
        entity.setOrderIndex(chapter.getOrderIndex());
        entity.setStatus(chapter.getStatus().name());
        entity.setWordCount(chapter.getWordCount().getValue());
        return entity;
    }
}
