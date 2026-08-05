-- StoryVerse Notification Module - V8 Migration (MySQL)

CREATE TABLE notifications (
    id              CHAR(36) PRIMARY KEY,
    recipient_id    CHAR(36) NOT NULL,
    type            VARCHAR(30) NOT NULL,
    channel         VARCHAR(10) NOT NULL,
    status          VARCHAR(15) NOT NULL DEFAULT 'QUEUED',
    title           VARCHAR(200) NOT NULL,
    body            TEXT,
    action_url      VARCHAR(500),
    retry_count     INTEGER NOT NULL DEFAULT 0,
    sent_at         TIMESTAMP NULL,
    read_at         TIMESTAMP NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_notifications_recipient ON notifications (recipient_id);
CREATE INDEX idx_notifications_recipient_status ON notifications (recipient_id, status);
CREATE INDEX idx_notifications_created ON notifications (created_at);

CREATE TABLE notification_preferences (
    id          CHAR(36) PRIMARY KEY,
    user_id     CHAR(36) NOT NULL,
    type        VARCHAR(30) NOT NULL,
    channel     VARCHAR(10) NOT NULL,
    enabled     BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE KEY uq_notification_pref (user_id, type, channel)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_notification_pref_user ON notification_preferences (user_id);
