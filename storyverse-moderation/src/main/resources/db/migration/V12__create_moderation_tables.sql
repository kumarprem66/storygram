-- StoryVerse Moderation Module - V12 Migration (MySQL)

CREATE TABLE moderation_cases (
    id                      CHAR(36) PRIMARY KEY,
    reporter_id             CHAR(36) NOT NULL,
    target_id               CHAR(36) NOT NULL,
    target_type             VARCHAR(30) NOT NULL,
    reason                  VARCHAR(30) NOT NULL,
    description             TEXT,
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    action                  VARCHAR(30) NOT NULL DEFAULT 'NONE',
    assigned_moderator_id   CHAR(36),
    moderator_notes         TEXT,
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at             TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_moderation_cases_status ON moderation_cases (status);
CREATE INDEX idx_moderation_cases_target ON moderation_cases (target_id, target_type);
CREATE INDEX idx_moderation_cases_moderator ON moderation_cases (assigned_moderator_id);
CREATE INDEX idx_moderation_cases_reporter ON moderation_cases (reporter_id);
