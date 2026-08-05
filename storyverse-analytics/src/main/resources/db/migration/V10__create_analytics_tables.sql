-- ============================================================
-- StoryVerse Analytics Module - V10 Migration
-- ============================================================

CREATE TABLE analytics_events (
    id              UUID PRIMARY KEY,
    metric_type     VARCHAR(30) NOT NULL,
    entity_id       UUID,
    user_id         UUID,
    metadata        JSONB,
    occurred_at     TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_analytics_events_type ON analytics_events (metric_type);
CREATE INDEX idx_analytics_events_occurred ON analytics_events (occurred_at DESC);
CREATE INDEX idx_analytics_events_entity ON analytics_events (entity_id);
CREATE INDEX idx_analytics_events_user ON analytics_events (user_id);

CREATE TABLE aggregated_metrics (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    metric_type     VARCHAR(30) NOT NULL,
    time_window     VARCHAR(10) NOT NULL,
    period_start    DATE NOT NULL,
    count           BIGINT NOT NULL DEFAULT 0,
    entity_id       UUID,
    CONSTRAINT uq_aggregated_metrics UNIQUE (metric_type, time_window, period_start, entity_id)
);

CREATE INDEX idx_aggregated_metrics_type_window ON aggregated_metrics (metric_type, time_window, period_start);
