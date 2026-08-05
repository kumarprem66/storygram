package com.storyverse.story.adapters.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringDataStoryRepository extends JpaRepository<StoryJpaEntity, UUID> {

    Page<StoryJpaEntity> findByAuthorId(UUID authorId, Pageable pageable);

    Page<StoryJpaEntity> findByStatusAndVisibility(String status, String visibility, Pageable pageable);

    long countByAuthorId(UUID authorId);
}
