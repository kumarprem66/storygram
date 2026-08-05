-- ============================================================
-- StoryVerse Search Module - V6 Migration
-- Search metadata tracking (index state)
-- ============================================================

CREATE TABLE search_metadata (
    document_id     UUID NOT NULL,
    document_type   VARCHAR(30) NOT NULL,
    indexed_at      TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (document_id, document_type)
);

CREATE INDEX idx_search_metadata_type ON search_metadata (document_type);
