-- StoryVerse Reader Module - V4 Migration (MySQL)

CREATE TABLE reading_progress (
    id                          CHAR(36) PRIMARY KEY,
    user_id                     CHAR(36) NOT NULL,
    story_id                    CHAR(36) NOT NULL,
    current_chapter_id          CHAR(36),
    current_chapter_index       INTEGER NOT NULL DEFAULT 0,
    progress_percentage         DOUBLE NOT NULL DEFAULT 0,
    status                      VARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED',
    total_reading_time_seconds  BIGINT NOT NULL DEFAULT 0,
    last_read_at                TIMESTAMP NULL,
    started_at                  TIMESTAMP NULL,
    completed_at                TIMESTAMP NULL,
    created_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP NULL,
    created_by                  VARCHAR(255),
    updated_by                  VARCHAR(255),
    version                     BIGINT NOT NULL DEFAULT 0,
    UNIQUE KEY uq_reading_progress_user_story (user_id, story_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_reading_progress_user_id ON reading_progress (user_id);
CREATE INDEX idx_reading_progress_last_read ON reading_progress (user_id, last_read_at);

CREATE TABLE bookmarks (
    id          CHAR(36) PRIMARY KEY,
    user_id     CHAR(36) NOT NULL,
    story_id    CHAR(36) NOT NULL,
    chapter_id  CHAR(36) NOT NULL,
    note        VARCHAR(500),
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_bookmarks_user_id ON bookmarks (user_id);
CREATE INDEX idx_bookmarks_user_story ON bookmarks (user_id, story_id);

CREATE TABLE reading_collections (
    id          CHAR(36) PRIMARY KEY,
    user_id     CHAR(36) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    visibility  VARCHAR(10) NOT NULL DEFAULT 'PRIVATE',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NULL,
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    version     BIGINT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_reading_collections_user_id ON reading_collections (user_id);

CREATE TABLE collection_stories (
    collection_id CHAR(36) NOT NULL,
    story_id      CHAR(36) NOT NULL,
    PRIMARY KEY (collection_id, story_id),
    CONSTRAINT fk_collection_stories_collection FOREIGN KEY (collection_id) REFERENCES reading_collections (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
