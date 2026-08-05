-- ============================================================
-- StoryVerse Recommendation Module - V7 Migration
-- ============================================================

CREATE TABLE recommendation_feedback (
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL,
    story_id        UUID NOT NULL,
    feedback_type   VARCHAR(30) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_rec_feedback_user ON recommendation_feedback (user_id);
CREATE INDEX idx_rec_feedback_story ON recommendation_feedback (story_id);
