package com.storyverse.identity.ports;

import java.util.Optional;
import java.util.UUID;

/**
 * Port for email verification token management.
 * Handles generation, storage, and validation of verification tokens.
 */
public interface EmailVerificationPort {

    /**
     * Generates and stores a verification token for the user.
     * Returns the token string to be sent via email.
     */
    String generateVerificationToken(UUID userId, String email);

    /**
     * Validates a verification token and returns the associated user ID.
     */
    Optional<UUID> validateToken(String token);

    /**
     * Invalidates a verification token after use.
     */
    void invalidateToken(String token);
}
