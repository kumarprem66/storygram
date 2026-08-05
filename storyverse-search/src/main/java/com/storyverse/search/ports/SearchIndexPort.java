package com.storyverse.search.ports;

import com.storyverse.search.domain.SearchQuery;
import com.storyverse.search.domain.SearchResult;
import java.util.List;
import java.util.UUID;

/**
 * Port for search engine operations.
 * Implemented by OpenSearch/Elasticsearch adapter in production.
 */
public interface SearchIndexPort {
    void indexStory(UUID storyId, String title, String description, String authorName, UUID authorId,
                    String genre, java.util.Set<String> tags, String language, String ageRating,
                    double rating, int readCount, String coverImageUrl, java.time.Instant publishedAt);

    void removeStory(UUID storyId);

    List<SearchResult> search(SearchQuery query);

    List<String> autocomplete(String prefix, int limit);
}
