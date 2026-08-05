-- ============================================================
-- StoryVerse Moderation Module - V12 Migration
-- ============================================================

CREATE TABLE moderation_cases (
    id                      UUID PRIMARY KEY,
    reporter_id             UUID NOT NULL,
    target_id               UUID NOT NULL,
    target_type             VARCHAR(30) NOT NULL,
    reason                  VARCHAR(30) NOT NULL,
    description             TEXT,
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    action                  VARCHAR(30) NOT NULL DEFAULT 'NONE',
    assigned_moderator_id   UUID,
    moderator_notes         TEXT,
    created_at              TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    resolved_at             TIMESTAMPTZ
);

CREATE INDEX idx_moderation_cases_status ON moderation_cases (status);
CREATE INDEX idx_moderation_cases_target ON moderation_cases (target_id, target_type);
CREATE INDEX idx_moderation_cases_moderator ON moderation_cases (assigned_moderator_id);
CREATE INDEX idx_moderation_cases_reporter ON moderation_cases (reporter_id);
