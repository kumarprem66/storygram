-- ============================================================
-- StoryVerse Administration Module - V13 Migration
-- ============================================================

CREATE TABLE feature_flags (
    id                  UUID PRIMARY KEY,
    name                VARCHAR(100) NOT NULL UNIQUE,
    description         VARCHAR(500),
    status              VARCHAR(20) NOT NULL DEFAULT 'DISABLED',
    rollout_percentage  INTEGER NOT NULL DEFAULT 0,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ
);

CREATE UNIQUE INDEX idx_feature_flags_name ON feature_flags (name);

CREATE TABLE platform_config (
    id          UUID PRIMARY KEY,
    key         VARCHAR(100) NOT NULL UNIQUE,
    value       TEXT NOT NULL,
    description VARCHAR(500),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_platform_config_key ON platform_config (key);
