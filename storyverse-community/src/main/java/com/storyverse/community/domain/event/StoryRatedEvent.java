package com.storyverse.community.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class StoryRatedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final UUID storyId;
    private final int score;

    public StoryRatedEvent(UUID ratingId, UUID userId, UUID storyId, int score, EventMetadata metadata) {
        super(ratingId, metadata);
        this.userId = userId;
        this.storyId = storyId;
        this.score = score;
    }

    @Override public String getEventType() { return "StoryRated"; }
    @Override public String getAggregateType() { return "Rating"; }
    public UUID getUserId() { return userId; }
    public UUID getStoryId() { return storyId; }
    public int getScore() { return score; }
}
