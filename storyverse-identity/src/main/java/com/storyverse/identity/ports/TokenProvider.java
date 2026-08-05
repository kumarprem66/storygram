package com.storyverse.identity.ports;

import com.storyverse.identity.domain.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Port for JWT token generation and validation.
 * Implemented by the security adapter.
 */
public interface TokenProvider {

    /**
     * Generates an access token for the given user.
     */
    String generateAccessToken(User user);

    /**
     * Generates a refresh token for the given user.
     */
    String generateRefreshToken(User user);

    /**
     * Validates an access token and returns the user ID if valid.
     */
    Optional<UUID> validateAccessToken(String token);

    /**
     * Validates a refresh token and returns the user ID if valid.
     */
    Optional<UUID> validateRefreshToken(String token);

    /**
     * Revokes a refresh token (e.g., on logout).
     */
    void revokeRefreshToken(String token);

    /**
     * Revokes all refresh tokens for a user.
     */
    void revokeAllUserTokens(UUID userId);
}
