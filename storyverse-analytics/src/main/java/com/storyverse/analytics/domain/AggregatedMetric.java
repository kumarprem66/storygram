package com.storyverse.analytics.domain;

import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.domain.vo.TimeWindow;
import java.time.LocalDate;
import java.util.UUID;

public record AggregatedMetric(
        UUID id,
        MetricType metricType,
        TimeWindow timeWindow,
        LocalDate periodStart,
        long count,
        UUID entityId
) {}
