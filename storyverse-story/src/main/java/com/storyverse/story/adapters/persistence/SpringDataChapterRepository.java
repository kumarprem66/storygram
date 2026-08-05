package com.storyverse.story.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface SpringDataChapterRepository extends JpaRepository<ChapterJpaEntity, UUID> {

    List<ChapterJpaEntity> findByStoryIdOrderByOrderIndex(UUID storyId);

    int countByStoryId(UUID storyId);

    void deleteAllByStoryId(UUID storyId);
}
