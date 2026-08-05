-- StoryVerse Recommendation Module - V7 Migration (MySQL)

CREATE TABLE recommendation_feedback (
    id              CHAR(36) PRIMARY KEY,
    user_id         CHAR(36) NOT NULL,
    story_id        CHAR(36) NOT NULL,
    feedback_type   VARCHAR(30) NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_rec_feedback_user ON recommendation_feedback (user_id);
CREATE INDEX idx_rec_feedback_story ON recommendation_feedback (story_id);
