package com.storyverse.search.api;

import com.storyverse.search.application.AutocompleteUseCase;
import com.storyverse.search.application.SearchStoriesUseCase;
import com.storyverse.search.domain.SearchResult;
import com.storyverse.shared.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SearchStoriesUseCase searchStoriesUseCase;
    private final AutocompleteUseCase autocompleteUseCase;

    public SearchController(SearchStoriesUseCase searchStoriesUseCase, AutocompleteUseCase autocompleteUseCase) {
        this.searchStoriesUseCase = searchStoriesUseCase;
        this.autocompleteUseCase = autocompleteUseCase;
    }

    @GetMapping("/stories")
    public ResponseEntity<ApiResponse<List<SearchResult>>> searchStories(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String ageRating,
            @RequestParam(required = false) Set<String> tags,
            @RequestParam(defaultValue = "RELEVANCE") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<SearchResult> results = searchStoriesUseCase.execute(keyword, genre, language, ageRating, tags, sortBy, page, size);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<ApiResponse<List<String>>> autocomplete(
            @RequestParam String prefix,
            @RequestParam(defaultValue = "5") int limit) {
        List<String> suggestions = autocompleteUseCase.execute(prefix, limit);
        return ResponseEntity.ok(ApiResponse.success(suggestions));
    }
}
