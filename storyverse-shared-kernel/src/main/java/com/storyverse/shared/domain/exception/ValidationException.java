package com.storyverse.shared.domain.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Thrown when input validation fails.
 * Carries field-level validation errors for API response construction.
 */
public class ValidationException extends StoryVerseException {

    private final Map<String, List<String>> fieldErrors;

    public ValidationException(String message, Map<String, List<String>> fieldErrors) {
        super("VALIDATION_FAILED", message);
        this.fieldErrors = fieldErrors != null ? fieldErrors : Collections.emptyMap();
    }

    public ValidationException(String message) {
        this(message, Collections.emptyMap());
    }

    public Map<String, List<String>> getFieldErrors() {
        return Collections.unmodifiableMap(fieldErrors);
    }
}
