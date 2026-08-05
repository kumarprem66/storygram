-- ============================================================
-- StoryVerse Reader Module - V4 Migration
-- ============================================================

CREATE TABLE reading_progress (
    id                          UUID PRIMARY KEY,
    user_id                     UUID NOT NULL,
    story_id                    UUID NOT NULL,
    current_chapter_id          UUID,
    current_chapter_index       INTEGER NOT NULL DEFAULT 0,
    progress_percentage         DOUBLE PRECISION NOT NULL DEFAULT 0,
    status                      VARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED',
    total_reading_time_seconds  BIGINT NOT NULL DEFAULT 0,
    last_read_at                TIMESTAMPTZ,
    started_at                  TIMESTAMPTZ,
    completed_at                TIMESTAMPTZ,
    created_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMPTZ,
    created_by                  VARCHAR(255),
    updated_by                  VARCHAR(255),
    version                     BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT uq_reading_progress_user_story UNIQUE (user_id, story_id)
);

CREATE INDEX idx_reading_progress_user_id ON reading_progress (user_id);
CREATE INDEX idx_reading_progress_last_read ON reading_progress (user_id, last_read_at DESC);

CREATE TABLE bookmarks (
    id          UUID PRIMARY KEY,
    user_id     UUID NOT NULL,
    story_id    UUID NOT NULL,
    chapter_id  UUID NOT NULL,
    note        VARCHAR(500),
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_bookmarks_user_id ON bookmarks (user_id);
CREATE INDEX idx_bookmarks_user_story ON bookmarks (user_id, story_id);

CREATE TABLE reading_collections (
    id          UUID PRIMARY KEY,
    user_id     UUID NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    visibility  VARCHAR(10) NOT NULL DEFAULT 'PRIVATE',
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ,
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    version     BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_reading_collections_user_id ON reading_collections (user_id);

CREATE TABLE collection_stories (
    collection_id UUID NOT NULL,
    story_id      UUID NOT NULL,
    PRIMARY KEY (collection_id, story_id),
    CONSTRAINT fk_collection_stories_collection FOREIGN KEY (collection_id) REFERENCES reading_collections (id) ON DELETE CASCADE
);
