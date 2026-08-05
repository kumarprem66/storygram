-- StoryVerse AI Platform - V9 Migration (MySQL)

CREATE TABLE ai_requests (
    id              CHAR(36) PRIMARY KEY,
    user_id         CHAR(36) NOT NULL,
    task_type       VARCHAR(30) NOT NULL,
    provider        VARCHAR(20),
    status          VARCHAR(15) NOT NULL DEFAULT 'PENDING',
    input           TEXT,
    output          TEXT,
    input_tokens    INTEGER NOT NULL DEFAULT 0,
    output_tokens   INTEGER NOT NULL DEFAULT 0,
    latency_ms      BIGINT NOT NULL DEFAULT 0,
    error_message   TEXT,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at    TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_ai_requests_user ON ai_requests (user_id);
CREATE INDEX idx_ai_requests_task ON ai_requests (task_type);
CREATE INDEX idx_ai_requests_created ON ai_requests (created_at);
