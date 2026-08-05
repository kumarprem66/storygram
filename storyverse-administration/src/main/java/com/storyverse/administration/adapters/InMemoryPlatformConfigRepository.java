package com.storyverse.administration.adapters;

import com.storyverse.administration.domain.PlatformConfig;
import com.storyverse.administration.ports.PlatformConfigRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryPlatformConfigRepository implements PlatformConfigRepository {
    private final Map<String, PlatformConfig> store = new ConcurrentHashMap<>();

    @Override public Optional<PlatformConfig> findByKey(String key) { return Optional.ofNullable(store.get(key)); }
    @Override public List<PlatformConfig> findAll() { return new ArrayList<>(store.values()); }
    @Override public PlatformConfig save(PlatformConfig config) { store.put(config.getKey(), config); return config; }
}
