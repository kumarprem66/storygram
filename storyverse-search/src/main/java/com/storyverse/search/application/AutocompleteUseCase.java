package com.storyverse.search.application;

import com.storyverse.search.ports.SearchIndexPort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutocompleteUseCase {
    private final SearchIndexPort searchIndexPort;

    public AutocompleteUseCase(SearchIndexPort searchIndexPort) {
        this.searchIndexPort = searchIndexPort;
    }

    public List<String> execute(String prefix, int limit) {
        if (prefix == null || prefix.isBlank()) return List.of();
        return searchIndexPort.autocomplete(prefix.trim(), Math.min(limit, 10));
    }
}
