-- StoryVerse Search Module - V6 Migration (MySQL)

CREATE TABLE search_metadata (
    document_id     CHAR(36) NOT NULL,
    document_type   VARCHAR(30) NOT NULL,
    indexed_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (document_id, document_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_search_metadata_type ON search_metadata (document_type);
