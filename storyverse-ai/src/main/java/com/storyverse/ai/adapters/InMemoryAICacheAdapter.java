package com.storyverse.ai.adapters;

import com.storyverse.ai.ports.AICachePort;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAICacheAdapter implements AICachePort {
    private record CacheEntry(String value, Instant expiresAt) {}
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<String> get(String cacheKey) {
        CacheEntry entry = cache.get(cacheKey);
        if (entry == null || Instant.now().isAfter(entry.expiresAt())) {
            cache.remove(cacheKey);
            return Optional.empty();
        }
        return Optional.of(entry.value());
    }

    @Override
    public void put(String cacheKey, String response, long ttlSeconds) {
        cache.put(cacheKey, new CacheEntry(response, Instant.now().plusSeconds(ttlSeconds)));
    }
}
