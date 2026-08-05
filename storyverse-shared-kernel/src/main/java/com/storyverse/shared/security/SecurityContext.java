package com.storyverse.shared.security;

import java.util.Optional;

/**
 * Port interface for accessing the current security context.
 * Implemented by the infrastructure layer (Spring Security adapter).
 * Business modules depend on this abstraction, not on Spring Security directly.
 */
public interface SecurityContext {

    /**
     * Returns the currently authenticated user, if present.
     */
    Optional<AuthenticatedUser> getCurrentUser();

    /**
     * Returns the currently authenticated user or throws AuthenticationException.
     */
    AuthenticatedUser requireCurrentUser();

    /**
     * Returns the current user's ID or throws AuthenticationException.
     */
    default java.util.UUID requireUserId() {
        return requireCurrentUser().userId();
    }

    /**
     * Checks if any user is currently authenticated.
     */
    default boolean isAuthenticated() {
        return getCurrentUser().isPresent();
    }
}
