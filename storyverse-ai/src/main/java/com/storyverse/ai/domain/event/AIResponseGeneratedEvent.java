package com.storyverse.ai.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class AIResponseGeneratedEvent extends BaseDomainEvent {
    private final UUID userId;
    private final String taskType;
    private final String provider;
    private final int totalTokens;

    public AIResponseGeneratedEvent(UUID requestId, UUID userId, String taskType, String provider, int totalTokens, EventMetadata metadata) {
        super(requestId, metadata);
        this.userId = userId;
        this.taskType = taskType;
        this.provider = provider;
        this.totalTokens = totalTokens;
    }

    @Override public String getEventType() { return "AIResponseGenerated"; }
    @Override public String getAggregateType() { return "AIRequest"; }
    public UUID getUserId() { return userId; }
    public String getTaskType() { return taskType; }
    public String getProvider() { return provider; }
    public int getTotalTokens() { return totalTokens; }
}
