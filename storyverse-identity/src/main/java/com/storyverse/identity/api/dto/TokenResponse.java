package com.storyverse.identity.api.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
