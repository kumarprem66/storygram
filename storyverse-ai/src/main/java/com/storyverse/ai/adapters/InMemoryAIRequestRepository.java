package com.storyverse.ai.adapters;

import com.storyverse.ai.domain.AIRequest;
import com.storyverse.ai.ports.AIRequestRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAIRequestRepository implements AIRequestRepository {
    private final Map<UUID, AIRequest> store = new ConcurrentHashMap<>();

    @Override public AIRequest save(AIRequest request) { store.put(request.getId(), request); return request; }
    @Override public Optional<AIRequest> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<AIRequest> findByUserId(UUID userId) { return store.values().stream().filter(r -> r.getUserId().equals(userId)).toList(); }
}
