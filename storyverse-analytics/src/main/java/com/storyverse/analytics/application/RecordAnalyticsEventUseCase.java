package com.storyverse.analytics.application;

import com.storyverse.analytics.domain.AnalyticsEvent;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.ports.AnalyticsEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.UUID;

@Service
public class RecordAnalyticsEventUseCase {
    private final AnalyticsEventRepository repository;

    public RecordAnalyticsEventUseCase(AnalyticsEventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(MetricType metricType, UUID entityId, UUID userId, Map<String, String> metadata) {
        AnalyticsEvent event = AnalyticsEvent.create(UUID.randomUUID(), metricType, entityId, userId, metadata);
        repository.save(event);
    }
}
