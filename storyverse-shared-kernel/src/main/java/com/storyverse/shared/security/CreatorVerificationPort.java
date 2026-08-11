package com.storyverse.shared.security;

import java.util.UUID;

/**
 * Cross-module port to verify if a user is an active creator.
 * Lives in shared-kernel because multiple modules may need this check.
 * Implemented by the Creator module's adapter.
 */
public interface CreatorVerificationPort {

    /**
     * Returns true if the user has an active creator profile.
     */
    boolean isActiveCreator(UUID userId);
}
