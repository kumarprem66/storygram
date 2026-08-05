-- ============================================================
-- StoryVerse Billing Module - V11 Migration
-- ============================================================

CREATE TABLE subscriptions (
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL,
    plan            VARCHAR(20) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    start_date      TIMESTAMPTZ NOT NULL,
    end_date        TIMESTAMPTZ,
    cancelled_at    TIMESTAMPTZ,
    auto_renew      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ,
    version         BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_subscriptions_user ON subscriptions (user_id);
CREATE INDEX idx_subscriptions_status ON subscriptions (status);

CREATE TABLE payments (
    id                      UUID PRIMARY KEY,
    user_id                 UUID NOT NULL,
    subscription_id         UUID,
    amount                  DECIMAL(10, 2) NOT NULL,
    currency                VARCHAR(3) NOT NULL DEFAULT 'INR',
    status                  VARCHAR(15) NOT NULL DEFAULT 'PENDING',
    provider_transaction_id VARCHAR(100),
    payment_method          VARCHAR(30),
    created_at              TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_payments_user ON payments (user_id);
CREATE INDEX idx_payments_subscription ON payments (subscription_id);
CREATE INDEX idx_payments_status ON payments (status);
