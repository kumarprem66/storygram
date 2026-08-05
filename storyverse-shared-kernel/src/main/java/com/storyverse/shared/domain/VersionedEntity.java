package com.storyverse.shared.domain;

import java.util.UUID;

/**
 * Base entity with optimistic locking support.
 * Uses a version field for concurrent modification detection.
 */
public abstract class VersionedEntity extends AuditableEntity {

    private long version;

    protected VersionedEntity(UUID id) {
        super(id);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
