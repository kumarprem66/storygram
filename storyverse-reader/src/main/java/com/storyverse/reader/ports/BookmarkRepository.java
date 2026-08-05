package com.storyverse.reader.ports;

import com.storyverse.reader.domain.Bookmark;
import java.util.*;

public interface BookmarkRepository {
    Optional<Bookmark> findById(UUID id);
    List<Bookmark> findByUserId(UUID userId);
    List<Bookmark> findByUserIdAndStoryId(UUID userId, UUID storyId);
    Bookmark save(Bookmark bookmark);
    void delete(UUID id);
}
