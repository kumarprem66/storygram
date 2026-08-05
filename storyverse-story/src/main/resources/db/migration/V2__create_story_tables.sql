-- StoryVerse Story Module - V2 Migration (MySQL)

CREATE TABLE stories (
    id                  CHAR(36) PRIMARY KEY,
    author_id           CHAR(36) NOT NULL,
    title               VARCHAR(200) NOT NULL,
    subtitle            VARCHAR(200),
    description         VARCHAR(2000),
    genre               VARCHAR(30),
    language            VARCHAR(10),
    age_rating          VARCHAR(20) DEFAULT 'EVERYONE',
    visibility          VARCHAR(20) NOT NULL DEFAULT 'PRIVATE',
    status              VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    cover_image_url     VARCHAR(500),
    total_word_count    INTEGER NOT NULL DEFAULT 0,
    published_at        TIMESTAMP NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NULL,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    version             BIGINT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_stories_author_id ON stories (author_id);
CREATE INDEX idx_stories_status ON stories (status);
CREATE INDEX idx_stories_visibility ON stories (visibility);
CREATE INDEX idx_stories_genre ON stories (genre);
CREATE INDEX idx_stories_published_at ON stories (published_at);

CREATE TABLE story_tags (
    story_id    CHAR(36) NOT NULL,
    tag         VARCHAR(50) NOT NULL,
    PRIMARY KEY (story_id, tag),
    CONSTRAINT fk_story_tags_story FOREIGN KEY (story_id) REFERENCES stories (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE chapters (
    id              CHAR(36) PRIMARY KEY,
    story_id        CHAR(36) NOT NULL,
    title           VARCHAR(200) NOT NULL,
    content         TEXT,
    order_index     INTEGER NOT NULL DEFAULT 0,
    status          VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    word_count      INTEGER NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NULL,
    created_by      VARCHAR(255),
    updated_by      VARCHAR(255),
    version         BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_chapters_story FOREIGN KEY (story_id) REFERENCES stories (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_chapters_story_id ON chapters (story_id);
CREATE INDEX idx_chapters_story_order ON chapters (story_id, order_index);
