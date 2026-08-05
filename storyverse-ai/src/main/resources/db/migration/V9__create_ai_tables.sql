-- ============================================================
-- StoryVerse AI Platform - V9 Migration
-- ============================================================

CREATE TABLE ai_requests (
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL,
    task_type       VARCHAR(30) NOT NULL,
    provider        VARCHAR(20),
    status          VARCHAR(15) NOT NULL DEFAULT 'PENDING',
    input           TEXT,
    output          TEXT,
    input_tokens    INTEGER NOT NULL DEFAULT 0,
    output_tokens   INTEGER NOT NULL DEFAULT 0,
    latency_ms      BIGINT NOT NULL DEFAULT 0,
    error_message   TEXT,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    completed_at    TIMESTAMPTZ
);

CREATE INDEX idx_ai_requests_user ON ai_requests (user_id);
CREATE INDEX idx_ai_requests_task ON ai_requests (task_type);
CREATE INDEX idx_ai_requests_created ON ai_requests (created_at DESC);
