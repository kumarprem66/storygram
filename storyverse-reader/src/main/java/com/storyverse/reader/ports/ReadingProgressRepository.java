package com.storyverse.reader.ports;

import com.storyverse.reader.domain.ReadingProgress;
import java.util.*;

public interface ReadingProgressRepository {
    Optional<ReadingProgress> findById(UUID id);
    Optional<ReadingProgress> findByUserIdAndStoryId(UUID userId, UUID storyId);
    List<ReadingProgress> findByUserId(UUID userId);
    ReadingProgress save(ReadingProgress progress);
    void delete(UUID id);
}
