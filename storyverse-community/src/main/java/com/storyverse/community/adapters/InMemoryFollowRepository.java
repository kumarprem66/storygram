package com.storyverse.community.adapters;

import com.storyverse.community.domain.Follow;
import com.storyverse.community.ports.FollowRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryFollowRepository implements FollowRepository {
    private final Map<UUID, Follow> store = new ConcurrentHashMap<>();

    @Override public Optional<Follow> findByFollowerIdAndFollowingId(UUID followerId, UUID followingId) {
        return store.values().stream().filter(f -> f.getFollowerId().equals(followerId) && f.getFollowingId().equals(followingId)).findFirst();
    }
    @Override public List<Follow> findFollowers(UUID userId) { return store.values().stream().filter(f -> f.getFollowingId().equals(userId)).toList(); }
    @Override public List<Follow> findFollowing(UUID userId) { return store.values().stream().filter(f -> f.getFollowerId().equals(userId)).toList(); }
    @Override public boolean exists(UUID followerId, UUID followingId) { return findByFollowerIdAndFollowingId(followerId, followingId).isPresent(); }
    @Override public Follow save(Follow follow) { store.put(follow.getId(), follow); return follow; }
    @Override public void delete(UUID id) { store.remove(id); }
}
