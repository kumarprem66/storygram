package com.storyverse.analytics.adapters;

import com.storyverse.analytics.domain.AnalyticsEvent;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.ports.AnalyticsEventRepository;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class InMemoryAnalyticsEventRepository implements AnalyticsEventRepository {
    private final Deque<AnalyticsEvent> events = new ConcurrentLinkedDeque<>();

    @Override public AnalyticsEvent save(AnalyticsEvent event) { events.add(event); return event; }

    @Override public List<AnalyticsEvent> findByMetricType(MetricType type, Instant from, Instant to) {
        return events.stream().filter(e -> e.getMetricType() == type && !e.getOccurredAt().isBefore(from) && e.getOccurredAt().isBefore(to)).toList();
    }

    @Override public long countByMetricType(MetricType type, Instant from, Instant to) {
        return events.stream().filter(e -> e.getMetricType() == type && !e.getOccurredAt().isBefore(from) && e.getOccurredAt().isBefore(to)).count();
    }

    @Override public long countByMetricTypeAndEntityId(MetricType type, UUID entityId, Instant from, Instant to) {
        return events.stream().filter(e -> e.getMetricType() == type && entityId.equals(e.getEntityId()) && !e.getOccurredAt().isBefore(from) && e.getOccurredAt().isBefore(to)).count();
    }
}
