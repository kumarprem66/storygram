package com.storyverse.administration.adapters;

import com.storyverse.administration.domain.FeatureFlag;
import com.storyverse.administration.ports.FeatureFlagRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryFeatureFlagRepository implements FeatureFlagRepository {
    private final Map<UUID, FeatureFlag> store = new ConcurrentHashMap<>();

    @Override public Optional<FeatureFlag> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public Optional<FeatureFlag> findByName(String name) { return store.values().stream().filter(f -> f.getName().equals(name)).findFirst(); }
    @Override public List<FeatureFlag> findAll() { return new ArrayList<>(store.values()); }
    @Override public FeatureFlag save(FeatureFlag flag) { store.put(flag.getId(), flag); return flag; }
    @Override public void delete(UUID id) { store.remove(id); }
}
