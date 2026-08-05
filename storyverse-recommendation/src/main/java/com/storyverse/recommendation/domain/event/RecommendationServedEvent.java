package com.storyverse.recommendation.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class RecommendationServedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final String recommendationType;
    private final int itemCount;

    public RecommendationServedEvent(UUID id, UUID userId, String recommendationType, int itemCount, EventMetadata metadata) {
        super(id, metadata);
        this.userId = userId;
        this.recommendationType = recommendationType;
        this.itemCount = itemCount;
    }

    @Override public String getEventType() { return "RecommendationServed"; }
    @Override public String getAggregateType() { return "Recommendation"; }
    public UUID getUserId() { return userId; }
    public String getRecommendationType() { return recommendationType; }
    public int getItemCount() { return itemCount; }
}
