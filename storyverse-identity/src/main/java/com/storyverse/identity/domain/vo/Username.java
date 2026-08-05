package com.storyverse.identity.domain.vo;

import com.storyverse.shared.domain.BaseValueObject;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Username value object. Alphanumeric + underscores, 3-30 chars.
 */
public final class Username extends BaseValueObject {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,30}$");

    private final String value;

    private Username(String value) {
        this.value = value;
    }

    public static Username of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
        String trimmed = value.trim();
        if (!USERNAME_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Username must be 3-30 alphanumeric characters or underscores");
        }
        return new Username(trimmed);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return value.equalsIgnoreCase(username.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
