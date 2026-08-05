package com.storyverse.search.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.search.domain.event.SearchIndexedEvent;
import com.storyverse.search.ports.SearchIndexPort;
import com.storyverse.search.ports.SearchMetadataRepository;
import com.storyverse.shared.domain.event.EventMetadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class IndexStoryUseCase {
    private final SearchIndexPort searchIndexPort;
    private final SearchMetadataRepository metadataRepository;
    private final EventPublisher eventPublisher;

    public IndexStoryUseCase(SearchIndexPort searchIndexPort, SearchMetadataRepository metadataRepository, EventPublisher eventPublisher) {
        this.searchIndexPort = searchIndexPort;
        this.metadataRepository = metadataRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void execute(UUID storyId, String title, String description, String authorName, UUID authorId,
                        String genre, Set<String> tags, String language, String ageRating,
                        double rating, int readCount, String coverImageUrl, Instant publishedAt) {
        searchIndexPort.indexStory(storyId, title, description, authorName, authorId, genre, tags,
                language, ageRating, rating, readCount, coverImageUrl, publishedAt);
        metadataRepository.recordIndexed(storyId, "STORY", Instant.now());
        eventPublisher.publish(new SearchIndexedEvent(storyId, "STORY", EventMetadata.createNew("system")));
    }
}
