package com.storyverse.analytics.ports;

import com.storyverse.analytics.domain.AnalyticsEvent;
import com.storyverse.analytics.domain.vo.MetricType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AnalyticsEventRepository {
    AnalyticsEvent save(AnalyticsEvent event);
    List<AnalyticsEvent> findByMetricType(MetricType type, Instant from, Instant to);
    long countByMetricType(MetricType type, Instant from, Instant to);
    long countByMetricTypeAndEntityId(MetricType type, UUID entityId, Instant from, Instant to);
}
