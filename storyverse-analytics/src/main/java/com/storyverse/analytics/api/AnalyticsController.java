package com.storyverse.analytics.api;

import com.storyverse.analytics.application.*;
import com.storyverse.analytics.domain.AggregatedMetric;
import com.storyverse.analytics.domain.DashboardData;
import com.storyverse.analytics.domain.vo.MetricType;
import com.storyverse.analytics.domain.vo.TimeWindow;
import com.storyverse.shared.api.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {
    private final GetDashboardUseCase getDashboardUseCase;
    private final GetMetricsTrendUseCase getMetricsTrendUseCase;

    public AnalyticsController(GetDashboardUseCase getDashboardUseCase, GetMetricsTrendUseCase getMetricsTrendUseCase) {
        this.getDashboardUseCase = getDashboardUseCase;
        this.getMetricsTrendUseCase = getMetricsTrendUseCase;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardData>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(getDashboardUseCase.execute()));
    }

    @GetMapping("/trends")
    public ResponseEntity<ApiResponse<List<AggregatedMetric>>> getTrends(
            @RequestParam String metricType,
            @RequestParam(defaultValue = "DAILY") String timeWindow,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success(
                getMetricsTrendUseCase.execute(MetricType.valueOf(metricType.toUpperCase()),
                        TimeWindow.valueOf(timeWindow.toUpperCase()), from, to)));
    }
}
