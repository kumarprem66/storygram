package com.storyverse.search.domain;

import com.storyverse.search.domain.vo.SearchSortOption;
import java.util.Set;

public record SearchQuery(
        String keyword,
        String genre,
        String language,
        String ageRating,
        Set<String> tags,
        SearchSortOption sortBy,
        int page,
        int size
) {
    public SearchQuery {
        if (page < 0) page = 0;
        if (size <= 0) size = 20;
        if (size > 100) size = 100;
        if (sortBy == null) sortBy = SearchSortOption.RELEVANCE;
    }

    public static SearchQuery of(String keyword, int page, int size) {
        return new SearchQuery(keyword, null, null, null, null, SearchSortOption.RELEVANCE, page, size);
    }
}
