package com.storyverse.identity.domain.vo;

import com.storyverse.shared.domain.BaseValueObject;

/**
 * Hashed password value object. Never stores plaintext.
 */
public final class HashedPassword extends BaseValueObject {

    private final String value;

    private HashedPassword(String value) {
        this.value = value;
    }

    public static HashedPassword of(String hashedValue) {
        if (hashedValue == null || hashedValue.isBlank()) {
            throw new IllegalArgumentException("Hashed password must not be blank");
        }
        return new HashedPassword(hashedValue);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashedPassword that = (HashedPassword) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "HashedPassword{***}";
    }
}
