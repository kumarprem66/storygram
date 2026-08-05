-- StoryVerse Administration Module - V13 Migration (MySQL)

CREATE TABLE feature_flags (
    id                  CHAR(36) PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    description         VARCHAR(500),
    status              VARCHAR(20) NOT NULL DEFAULT 'DISABLED',
    rollout_percentage  INTEGER NOT NULL DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NULL,
    UNIQUE KEY uq_feature_flags_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE platform_config (
    id          CHAR(36) PRIMARY KEY,
    `key`       VARCHAR(100) NOT NULL,
    value       TEXT NOT NULL,
    description VARCHAR(500),
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_platform_config_key (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
