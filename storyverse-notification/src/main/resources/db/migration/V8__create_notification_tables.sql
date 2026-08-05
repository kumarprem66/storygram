-- ============================================================
-- StoryVerse Notification Module - V8 Migration
-- ============================================================

CREATE TABLE notifications (
    id              UUID PRIMARY KEY,
    recipient_id    UUID NOT NULL,
    type            VARCHAR(30) NOT NULL,
    channel         VARCHAR(10) NOT NULL,
    status          VARCHAR(15) NOT NULL DEFAULT 'QUEUED',
    title           VARCHAR(200) NOT NULL,
    body            TEXT,
    action_url      VARCHAR(500),
    retry_count     INTEGER NOT NULL DEFAULT 0,
    sent_at         TIMESTAMPTZ,
    read_at         TIMESTAMPTZ,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_notifications_recipient ON notifications (recipient_id);
CREATE INDEX idx_notifications_recipient_status ON notifications (recipient_id, status);
CREATE INDEX idx_notifications_created ON notifications (created_at DESC);

CREATE TABLE notification_preferences (
    id          UUID PRIMARY KEY,
    user_id     UUID NOT NULL,
    type        VARCHAR(30) NOT NULL,
    channel     VARCHAR(10) NOT NULL,
    enabled     BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_notification_pref UNIQUE (user_id, type, channel)
);

CREATE INDEX idx_notification_pref_user ON notification_preferences (user_id);
