package com.storyverse.shared.domain.exception;

/**
 * Base exception for all StoryVerse application exceptions.
 * Every custom exception in the platform extends this class.
 */
public abstract class StoryVerseException extends RuntimeException {

    private final String errorCode;

    protected StoryVerseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected StoryVerseException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
