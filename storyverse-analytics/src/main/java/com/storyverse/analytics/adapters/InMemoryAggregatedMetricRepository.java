package com.storyverse.analytics.adapters;

import com.storyverse.analytics.domain.AggregatedMetric;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.domain.vo.TimeWindow;
import com.storyverse.analytics.ports.AggregatedMetricRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InMemoryAggregatedMetricRepository implements AggregatedMetricRepository {
    private final List<AggregatedMetric> metrics = new CopyOnWriteArrayList<>();

    @Override public void save(AggregatedMetric metric) { metrics.add(metric); }

    @Override public List<AggregatedMetric> findByTypeAndWindow(MetricType type, TimeWindow window, LocalDate from, LocalDate to) {
        return metrics.stream().filter(m -> m.metricType() == type && m.timeWindow() == window && !m.periodStart().isBefore(from) && !m.periodStart().isAfter(to)).toList();
    }

    @Override public List<AggregatedMetric> findByEntityId(UUID entityId, MetricType type, TimeWindow window, LocalDate from, LocalDate to) {
        return metrics.stream().filter(m -> entityId.equals(m.entityId()) && m.metricType() == type && m.timeWindow() == window && !m.periodStart().isBefore(from) && !m.periodStart().isAfter(to)).toList();
    }
}
