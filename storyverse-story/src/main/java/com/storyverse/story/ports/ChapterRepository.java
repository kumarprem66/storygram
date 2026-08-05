package com.storyverse.story.ports;

import com.storyverse.story.domain.Chapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository port for Chapter entity persistence.
 */
public interface ChapterRepository {

    Optional<Chapter> findById(UUID id);

    List<Chapter> findByStoryIdOrderByOrderIndex(UUID storyId);

    int countByStoryId(UUID storyId);

    Chapter save(Chapter chapter);

    void delete(UUID id);

    void deleteAllByStoryId(UUID storyId);
}
