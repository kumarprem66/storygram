package com.storyverse.reader.adapters.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
interface SpringDataReadingProgressRepository extends JpaRepository<ReadingProgressJpaEntity, UUID> {
    Optional<ReadingProgressJpaEntity> findByUserIdAndStoryId(UUID userId, UUID storyId);
    List<ReadingProgressJpaEntity> findByUserId(UUID userId);
}
