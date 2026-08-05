package com.storyverse.ai.ports;

import java.util.Optional;

/**
 * Port for caching deterministic AI responses.
 */
public interface AICachePort {
    Optional<String> get(String cacheKey);
    void put(String cacheKey, String response, long ttlSeconds);
}
