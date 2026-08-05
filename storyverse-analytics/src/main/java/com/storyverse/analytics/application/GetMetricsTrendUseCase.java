package com.storyverse.analytics.application;

import com.storyverse.analytics.domain.AggregatedMetric;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.domain.vo.TimeWindow;
import com.storyverse.analytics.ports.AggregatedMetricRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class GetMetricsTrendUseCase {
    private final AggregatedMetricRepository repository;

    public GetMetricsTrendUseCase(AggregatedMetricRepository repository) {
        this.repository = repository;
    }

    public List<AggregatedMetric> execute(MetricType metricType, TimeWindow timeWindow, LocalDate from, LocalDate to) {
        return repository.findByTypeAndWindow(metricType, timeWindow, from, to);
    }
}
