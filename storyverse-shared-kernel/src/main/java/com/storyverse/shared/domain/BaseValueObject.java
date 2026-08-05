package com.storyverse.shared.domain;

/**
 * Base class for value objects.
 * Value objects are defined by their attributes, not by identity.
 * Subclasses must implement equals() and hashCode() based on their fields.
 */
public abstract class BaseValueObject {

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
