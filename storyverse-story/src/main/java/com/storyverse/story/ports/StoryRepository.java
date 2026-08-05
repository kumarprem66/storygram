package com.storyverse.story.ports;

import com.storyverse.shared.api.PageRequest;
import com.storyverse.story.domain.Story;
import com.storyverse.story.domain.vo.StoryStatus;
import com.storyverse.story.domain.vo.Visibility;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository port for Story aggregate persistence.
 */
public interface StoryRepository {

    Optional<Story> findById(UUID id);

    List<Story> findByAuthorId(UUID authorId, PageRequest pageRequest);

    List<Story> findByStatusAndVisibility(StoryStatus status, Visibility visibility, PageRequest pageRequest);

    long countByAuthorId(UUID authorId);

    boolean existsById(UUID id);

    Story save(Story story);

    void delete(UUID id);
}
