package com.storyverse.reader.ports;

import com.storyverse.reader.domain.ReadingCollection;
import java.util.*;

public interface ReadingCollectionRepository {
    Optional<ReadingCollection> findById(UUID id);
    List<ReadingCollection> findByUserId(UUID userId);
    ReadingCollection save(ReadingCollection collection);
    void delete(UUID id);
}
