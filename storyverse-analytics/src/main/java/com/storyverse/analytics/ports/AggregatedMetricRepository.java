package com.storyverse.analytics.ports;

import com.storyverse.analytics.domain.AggregatedMetric;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.domain.vo.TimeWindow;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AggregatedMetricRepository {
    void save(AggregatedMetric metric);
    List<AggregatedMetric> findByTypeAndWindow(MetricType type, TimeWindow window, LocalDate from, LocalDate to);
    List<AggregatedMetric> findByEntityId(UUID entityId, MetricType type, TimeWindow window, LocalDate from, LocalDate to);
}
