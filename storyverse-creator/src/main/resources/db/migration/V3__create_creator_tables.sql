-- StoryVerse Creator Module - V3 Migration (MySQL)

CREATE TABLE creator_profiles (
    id                      CHAR(36) PRIMARY KEY,
    user_id                 CHAR(36) NOT NULL,
    display_name            VARCHAR(100) NOT NULL,
    biography               VARCHAR(2000),
    avatar_url              VARCHAR(500),
    banner_url              VARCHAR(500),
    website                 VARCHAR(500),
    language                VARCHAR(10),
    status                  VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    verification_status     VARCHAR(20) NOT NULL DEFAULT 'UNVERIFIED',
    follower_count          INTEGER NOT NULL DEFAULT 0,
    story_count             INTEGER NOT NULL DEFAULT 0,
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP NULL,
    created_by              VARCHAR(255),
    updated_by              VARCHAR(255),
    version                 BIGINT NOT NULL DEFAULT 0,
    UNIQUE KEY uq_creator_profiles_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_creator_profiles_user_id ON creator_profiles (user_id);
CREATE INDEX idx_creator_profiles_status ON creator_profiles (status);

CREATE TABLE creator_genres (
    creator_id  CHAR(36) NOT NULL,
    genre       VARCHAR(50) NOT NULL,
    PRIMARY KEY (creator_id, genre),
    CONSTRAINT fk_creator_genres_creator FOREIGN KEY (creator_id) REFERENCES creator_profiles (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE creator_pen_names (
    creator_id  CHAR(36) NOT NULL,
    pen_name    VARCHAR(100) NOT NULL,
    PRIMARY KEY (creator_id, pen_name),
    CONSTRAINT fk_creator_pen_names_creator FOREIGN KEY (creator_id) REFERENCES creator_profiles (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
