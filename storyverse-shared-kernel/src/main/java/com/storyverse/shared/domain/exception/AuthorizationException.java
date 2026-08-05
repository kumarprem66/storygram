package com.storyverse.shared.domain.exception;

/**
 * Thrown when a user does not have permission to perform the requested action.
 */
public class AuthorizationException extends StoryVerseException {

    public AuthorizationException(String message) {
        super("ACCESS_DENIED", message);
    }

    public AuthorizationException(String errorCode, String message) {
        super(errorCode, message);
    }
}
