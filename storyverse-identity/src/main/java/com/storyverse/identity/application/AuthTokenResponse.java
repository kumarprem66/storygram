package com.storyverse.identity.application;

/**
 * Response containing access and refresh tokens after successful authentication.
 */
public record AuthTokenResponse(
        String accessToken,
        String refreshToken
) {
}
