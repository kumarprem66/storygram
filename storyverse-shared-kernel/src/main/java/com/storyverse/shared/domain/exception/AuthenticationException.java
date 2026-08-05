package com.storyverse.shared.domain.exception;

/**
 * Thrown when authentication fails or credentials are invalid.
 */
public class AuthenticationException extends StoryVerseException {

    public AuthenticationException(String message) {
        super("AUTHENTICATION_FAILED", message);
    }

    public AuthenticationException(String errorCode, String message) {
        super(errorCode, message);
    }
}
