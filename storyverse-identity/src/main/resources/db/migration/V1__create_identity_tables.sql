-- ============================================================
-- StoryVerse Identity Module - V1 Migration
-- Creates: users, user_roles, email_verifications tables
-- ============================================================

-- Users table
CREATE TABLE users (
    id              UUID            PRIMARY KEY,
    email           VARCHAR(255)    NOT NULL,
    username        VARCHAR(30)     NOT NULL,
    password_hash   VARCHAR(255)    NOT NULL,
    display_name    VARCHAR(100)    NOT NULL,
    status          VARCHAR(30)     NOT NULL DEFAULT 'PENDING_VERIFICATION',
    email_verified  BOOLEAN         NOT NULL DEFAULT FALSE,
    mfa_enabled     BOOLEAN         NOT NULL DEFAULT FALSE,
    failed_login_attempts INTEGER   NOT NULL DEFAULT 0,
    last_login_at   TIMESTAMPTZ,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ,
    created_by      VARCHAR(255),
    updated_by      VARCHAR(255),
    version         BIGINT          NOT NULL DEFAULT 0,

    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT chk_users_status CHECK (status IN (
        'PENDING_VERIFICATION', 'ACTIVE', 'SUSPENDED', 'LOCKED', 'DEACTIVATED'
    ))
);

-- Indexes
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_users_status ON users (status);

-- User roles (element collection)
CREATE TABLE user_roles (
    user_id     UUID            NOT NULL,
    role        VARCHAR(20)     NOT NULL,

    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT chk_user_roles_role CHECK (role IN ('READER', 'CREATOR', 'MODERATOR', 'ADMIN'))
);

-- Email verification tokens
CREATE TABLE email_verifications (
    id          UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID            NOT NULL,
    token       VARCHAR(255)    NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    expires_at  TIMESTAMPTZ     NOT NULL,
    used        BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_email_verifications_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_email_verifications_token UNIQUE (token)
);

CREATE INDEX idx_email_verifications_token ON email_verifications (token);
CREATE INDEX idx_email_verifications_user_id ON email_verifications (user_id);
