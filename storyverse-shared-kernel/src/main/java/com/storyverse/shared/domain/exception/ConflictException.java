package com.storyverse.shared.domain.exception;

/**
 * Thrown when an operation conflicts with the current state of a resource.
 * Examples: duplicate registration, optimistic locking failure, concurrent modification.
 */
public class ConflictException extends StoryVerseException {

    public ConflictException(String message) {
        super("CONFLICT", message);
    }

    public ConflictException(String errorCode, String message) {
        super(errorCode, message);
    }
}
