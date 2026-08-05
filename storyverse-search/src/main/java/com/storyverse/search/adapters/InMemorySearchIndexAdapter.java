package com.storyverse.search.adapters;

import com.storyverse.search.domain.SearchQuery;
import com.storyverse.search.domain.SearchResult;
import com.storyverse.search.ports.SearchIndexPort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of SearchIndexPort for dev/test.
 * Production uses OpenSearch/Elasticsearch.
 */
@Component
public class InMemorySearchIndexAdapter implements SearchIndexPort {

    private final Map<UUID, SearchResult> index = new ConcurrentHashMap<>();

    @Override
    public void indexStory(UUID storyId, String title, String description, String authorName, UUID authorId,
                           String genre, Set<String> tags, String language, String ageRating,
                           double rating, int readCount, String coverImageUrl, Instant publishedAt) {
        index.put(storyId, new SearchResult(storyId, "STORY", title, description, authorName, authorId,
                genre, tags, language, rating, readCount, coverImageUrl, publishedAt, null, null));
    }

    @Override
    public void removeStory(UUID storyId) {
        index.remove(storyId);
    }

    @Override
    public List<SearchResult> search(SearchQuery query) {
        return index.values().stream()
                .filter(r -> query.keyword() == null || query.keyword().isBlank()
                        || r.title().toLowerCase().contains(query.keyword().toLowerCase())
                        || (r.description() != null && r.description().toLowerCase().contains(query.keyword().toLowerCase())))
                .filter(r -> query.genre() == null || query.genre().equalsIgnoreCase(r.genre()))
                .filter(r -> query.language() == null || query.language().equalsIgnoreCase(r.language()))
                .skip((long) query.page() * query.size())
                .limit(query.size())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> autocomplete(String prefix, int limit) {
        return index.values().stream()
                .map(SearchResult::title)
                .filter(t -> t.toLowerCase().startsWith(prefix.toLowerCase()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
