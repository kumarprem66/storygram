package com.storyverse.search.application;

import com.storyverse.search.domain.SearchQuery;
import com.storyverse.search.domain.SearchResult;
import com.storyverse.search.domain.vo.SearchSortOption;
import com.storyverse.search.ports.SearchIndexPort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class SearchStoriesUseCase {
    private final SearchIndexPort searchIndexPort;

    public SearchStoriesUseCase(SearchIndexPort searchIndexPort) {
        this.searchIndexPort = searchIndexPort;
    }

    public List<SearchResult> execute(String keyword, String genre, String language, String ageRating,
                                      Set<String> tags, String sortBy, int page, int size) {
        SearchSortOption sort = sortBy != null ? SearchSortOption.valueOf(sortBy.toUpperCase()) : SearchSortOption.RELEVANCE;
        SearchQuery query = new SearchQuery(keyword, genre, language, ageRating, tags, sort, page, size);
        return searchIndexPort.search(query);
    }
}
