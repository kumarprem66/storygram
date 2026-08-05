package com.storyverse.shared.domain.exception;

/**
 * Thrown when an infrastructure-level failure occurs.
 * Examples: database unavailable, message broker down, external service timeout.
 */
public class InfrastructureException extends StoryVerseException {

    public InfrastructureException(String message) {
        super("INFRASTRUCTURE_ERROR", message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super("INFRASTRUCTURE_ERROR", message, cause);
    }

    public InfrastructureException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
