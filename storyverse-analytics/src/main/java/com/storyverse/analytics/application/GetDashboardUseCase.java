package com.storyverse.analytics.application;

import com.storyverse.analytics.domain.DashboardData;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.ports.AnalyticsEventRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class GetDashboardUseCase {
    private final AnalyticsEventRepository repository;

    public GetDashboardUseCase(AnalyticsEventRepository repository) {
        this.repository = repository;
    }

    public DashboardData execute() {
        Instant now = Instant.now();
        Instant startOfDay = now.truncatedTo(ChronoUnit.DAYS);
        Instant epoch = Instant.EPOCH;

        return new DashboardData(
                repository.countByMetricType(MetricType.USER_REGISTERED, epoch, now),
                repository.countByMetricType(MetricType.STORY_PUBLISHED, epoch, now),
                repository.countByMetricType(MetricType.STORY_READ, epoch, now),
                repository.countByMetricType(MetricType.COMMENT_CREATED, epoch, now),
                repository.countByMetricType(MetricType.USER_REGISTERED, startOfDay, now),
                repository.countByMetricType(MetricType.STORY_PUBLISHED, startOfDay, now),
                repository.countByMetricType(MetricType.AI_REQUEST, startOfDay, now)
        );
    }
}
