-- StoryVerse Analytics Module - V10 Migration (MySQL)

CREATE TABLE analytics_events (
    id              CHAR(36) PRIMARY KEY,
    metric_type     VARCHAR(30) NOT NULL,
    entity_id       CHAR(36),
    user_id         CHAR(36),
    metadata        JSON,
    occurred_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_analytics_events_type ON analytics_events (metric_type);
CREATE INDEX idx_analytics_events_occurred ON analytics_events (occurred_at);
CREATE INDEX idx_analytics_events_entity ON analytics_events (entity_id);
CREATE INDEX idx_analytics_events_user ON analytics_events (user_id);

CREATE TABLE aggregated_metrics (
    id              CHAR(36) PRIMARY KEY,
    metric_type     VARCHAR(30) NOT NULL,
    time_window     VARCHAR(10) NOT NULL,
    period_start    DATE NOT NULL,
    count           BIGINT NOT NULL DEFAULT 0,
    entity_id       CHAR(36),
    UNIQUE KEY uq_aggregated_metrics (metric_type, time_window, period_start, entity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_aggregated_metrics_type_window ON aggregated_metrics (metric_type, time_window, period_start);
