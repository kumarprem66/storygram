package com.storyverse.community.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class UserFollowedEvent extends BaseDomainEvent {
    private final UUID followerId;
    private final UUID followingId;

    public UserFollowedEvent(UUID followId, UUID followerId, UUID followingId, EventMetadata metadata) {
        super(followId, metadata);
        this.followerId = followerId;
        this.followingId = followingId;
    }

    @Override public String getEventType() { return "UserFollowed"; }
    @Override public String getAggregateType() { return "Follow"; }
    public UUID getFollowerId() { return followerId; }
    public UUID getFollowingId() { return followingId; }
}
