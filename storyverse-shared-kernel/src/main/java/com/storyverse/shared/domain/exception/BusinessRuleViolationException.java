package com.storyverse.shared.domain.exception;

/**
 * Thrown when a business rule is violated.
 * Used for domain-level invariant enforcement.
 */
public class BusinessRuleViolationException extends StoryVerseException {

    public BusinessRuleViolationException(String errorCode, String message) {
        super(errorCode, message);
    }

    public BusinessRuleViolationException(String message) {
        super("BUSINESS_RULE_VIOLATION", message);
    }
}
