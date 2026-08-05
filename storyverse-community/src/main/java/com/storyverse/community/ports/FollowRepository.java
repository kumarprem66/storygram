package com.storyverse.community.ports;

import com.storyverse.community.domain.Follow;
import java.util.*;

public interface FollowRepository {
    Optional<Follow> findByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
    List<Follow> findFollowers(UUID userId);
    List<Follow> findFollowing(UUID userId);
    boolean exists(UUID followerId, UUID followingId);
    Follow save(Follow follow);
    void delete(UUID id);
}
