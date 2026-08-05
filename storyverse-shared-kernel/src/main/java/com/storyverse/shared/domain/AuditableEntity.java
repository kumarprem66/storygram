package com.storyverse.shared.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Base entity with audit metadata.
 * Tracks creation and modification timestamps and actors.
 */
public abstract class AuditableEntity extends BaseEntity {

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    protected AuditableEntity(UUID id) {
        super(id);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
