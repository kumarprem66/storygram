-- ============================================================
-- StoryVerse Story Module - V2 Migration
-- Creates: stories, story_tags, chapters tables
-- ============================================================

CREATE TABLE stories (
    id                  UUID            PRIMARY KEY,
    author_id           UUID            NOT NULL,
    title               VARCHAR(200)    NOT NULL,
    subtitle            VARCHAR(200),
    description         VARCHAR(2000),
    genre               VARCHAR(30),
    language            VARCHAR(10),
    age_rating          VARCHAR(20)     DEFAULT 'EVERYONE',
    visibility          VARCHAR(20)     NOT NULL DEFAULT 'PRIVATE',
    status              VARCHAR(20)     NOT NULL DEFAULT 'DRAFT',
    cover_image_url     VARCHAR(500),
    total_word_count    INTEGER         NOT NULL DEFAULT 0,
    published_at        TIMESTAMPTZ,
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    version             BIGINT          NOT NULL DEFAULT 0
);

CREATE INDEX idx_stories_author_id ON stories (author_id);
CREATE INDEX idx_stories_status ON stories (status);
CREATE INDEX idx_stories_visibility ON stories (visibility);
CREATE INDEX idx_stories_genre ON stories (genre);
CREATE INDEX idx_stories_published_at ON stories (published_at DESC);

CREATE TABLE story_tags (
    story_id    UUID            NOT NULL,
    tag         VARCHAR(50)     NOT NULL,

    PRIMARY KEY (story_id, tag),
    CONSTRAINT fk_story_tags_story FOREIGN KEY (story_id) REFERENCES stories (id) ON DELETE CASCADE
);

CREATE TABLE chapters (
    id              UUID            PRIMARY KEY,
    story_id        UUID            NOT NULL,
    title           VARCHAR(200)    NOT NULL,
    content         TEXT,
    order_index     INTEGER         NOT NULL DEFAULT 0,
    status          VARCHAR(20)     NOT NULL DEFAULT 'DRAFT',
    word_count      INTEGER         NOT NULL DEFAULT 0,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ,
    created_by      VARCHAR(255),
    updated_by      VARCHAR(255),
    version         BIGINT          NOT NULL DEFAULT 0,

    CONSTRAINT fk_chapters_story FOREIGN KEY (story_id) REFERENCES stories (id) ON DELETE CASCADE
);

CREATE INDEX idx_chapters_story_id ON chapters (story_id);
CREATE INDEX idx_chapters_story_order ON chapters (story_id, order_index);
