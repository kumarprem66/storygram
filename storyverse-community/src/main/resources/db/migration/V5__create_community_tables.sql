-- ============================================================
-- StoryVerse Community Module - V5 Migration
-- ============================================================

CREATE TABLE comments (
    id                  UUID PRIMARY KEY,
    user_id             UUID NOT NULL,
    story_id            UUID NOT NULL,
    parent_comment_id   UUID,
    content             TEXT NOT NULL,
    deleted             BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    version             BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_comments_story_id ON comments (story_id);
CREATE INDEX idx_comments_user_id ON comments (user_id);
CREATE INDEX idx_comments_parent ON comments (parent_comment_id);

CREATE TABLE reactions (
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL,
    target_id       UUID NOT NULL,
    target_type     VARCHAR(20) NOT NULL,
    reaction_type   VARCHAR(10) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_reactions_user_target UNIQUE (user_id, target_id, target_type)
);

CREATE INDEX idx_reactions_target ON reactions (target_id, target_type);

CREATE TABLE follows (
    id              UUID PRIMARY KEY,
    follower_id     UUID NOT NULL,
    following_id    UUID NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_follows UNIQUE (follower_id, following_id)
);

CREATE INDEX idx_follows_follower ON follows (follower_id);
CREATE INDEX idx_follows_following ON follows (following_id);

CREATE TABLE ratings (
    id          UUID PRIMARY KEY,
    user_id     UUID NOT NULL,
    story_id    UUID NOT NULL,
    score       INTEGER NOT NULL CHECK (score >= 1 AND score <= 5),
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ,
    CONSTRAINT uq_ratings_user_story UNIQUE (user_id, story_id)
);

CREATE INDEX idx_ratings_story_id ON ratings (story_id);
