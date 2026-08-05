package com.storyverse.moderation.adapters;

import com.storyverse.moderation.domain.ModerationCase;
import com.storyverse.moderation.domain.vo.ModerationStatus;
import com.storyverse.moderation.ports.ModerationCaseRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryModerationCaseRepository implements ModerationCaseRepository {
    private final Map<UUID, ModerationCase> store = new ConcurrentHashMap<>();

    @Override public ModerationCase save(ModerationCase c) { store.put(c.getId(), c); return c; }
    @Override public Optional<ModerationCase> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<ModerationCase> findByStatus(ModerationStatus status) { return store.values().stream().filter(c -> c.getStatus() == status).toList(); }
    @Override public List<ModerationCase> findByAssignedModeratorId(UUID moderatorId) { return store.values().stream().filter(c -> moderatorId.equals(c.getAssignedModeratorId())).toList(); }
}
