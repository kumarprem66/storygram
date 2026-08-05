package com.storyverse.community.application;

import com.storyverse.community.domain.Follow;
import com.storyverse.community.domain.event.UserFollowedEvent;
import com.storyverse.community.ports.FollowRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.ConflictException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class FollowUserUseCase {
    private final FollowRepository followRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public FollowUserUseCase(FollowRepository followRepository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.followRepository = followRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public UUID execute(UUID followingId) {
        UUID followerId = securityContext.requireUserId();
        if (followerId.equals(followingId)) throw new ConflictException("SELF_FOLLOW", "Cannot follow yourself");
        if (followRepository.exists(followerId, followingId)) throw new ConflictException("ALREADY_FOLLOWING", "Already following this user");
        Follow follow = Follow.create(UUID.randomUUID(), followerId, followingId);
        followRepository.save(follow);
        eventPublisher.publish(new UserFollowedEvent(follow.getId(), followerId, followingId, EventMetadata.createNew(followerId.toString())));
        return follow.getId();
    }
}
