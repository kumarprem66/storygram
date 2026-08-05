package com.storyverse.identity.api.dto;

import java.util.UUID;

public record UserRegisteredResponse(
        UUID userId,
        String message
) {
}
