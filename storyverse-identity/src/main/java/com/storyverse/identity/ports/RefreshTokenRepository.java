package com.storyverse.identity.ports;

import java.util.Optional;
import java.util.UUID;

/**
 * Port for refresh token persistence.
 * Stores refresh tokens with user association and expiry.
 */
public interface RefreshTokenRepository {

    /**
     * Saves a refresh token associated with a user.
     */
    void save(UUID userId, String token, long expirySeconds);

    /**
     * Finds the user ID associated with a valid refresh token.
     */
    Optional<UUID> findUserIdByToken(String token);

    /**
     * Deletes a specific refresh token.
     */
    void delete(String token);

    /**
     * Deletes all refresh tokens for a user.
     */
    void deleteAllByUserId(UUID userId);
}
