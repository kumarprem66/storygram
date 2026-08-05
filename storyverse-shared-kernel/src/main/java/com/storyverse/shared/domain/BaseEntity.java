package com.storyverse.shared.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Base class for all domain entities.
 * Identity is defined by the entity's unique identifier.
 */
public abstract class BaseEntity {

    private final UUID id;

    protected BaseEntity(UUID id) {
        this.id = Objects.requireNonNull(id, "Entity ID must not be null");
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + "}";
    }
}
