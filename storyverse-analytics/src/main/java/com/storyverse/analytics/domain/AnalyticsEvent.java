package com.storyverse.analytics.domain;

import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class AnalyticsEvent extends BaseEntity {
    private MetricType metricType;
    private UUID entityId;
    private UUID userId;
    private Map<String, String> metadata;
    private Instant occurredAt;

    private AnalyticsEvent(UUID id) { super(id); }

    public static AnalyticsEvent create(UUID id, MetricType metricType, UUID entityId, UUID userId, Map<String, String> metadata) {
        AnalyticsEvent e = new AnalyticsEvent(id);
        e.metricType = metricType;
        e.entityId = entityId;
        e.userId = userId;
        e.metadata = metadata;
        e.occurredAt = Instant.now();
        return e;
    }

    public static AnalyticsEvent reconstitute(UUID id, MetricType metricType, UUID entityId, UUID userId,
                                              Map<String, String> metadata, Instant occurredAt) {
        AnalyticsEvent e = new AnalyticsEvent(id);
        e.metricType = metricType;
        e.entityId = entityId;
        e.userId = userId;
        e.metadata = metadata;
        e.occurredAt = occurredAt;
        return e;
    }

    public MetricType getMetricType() { return metricType; }
    public UUID getEntityId() { return entityId; }
    public UUID getUserId() { return userId; }
    public Map<String, String> getMetadata() { return metadata; }
    public Instant getOccurredAt() { return occurredAt; }
}
