package com.storyverse.shared.security;

import java.util.Set;
import java.util.UUID;

/**
 * Represents the currently authenticated user.
 * Provider-independent: the Identity module populates this from JWT or session.
 * All business modules use this contract to access user context.
 */
public record AuthenticatedUser(
        UUID userId,
        String username,
        String email,
        Set<String> roles,
        Set<String> permissions
) {

    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }

    public boolean hasAnyRole(String... checkRoles) {
        if (roles == null) return false;
        for (String role : checkRoles) {
            if (roles.contains(role)) return true;
        }
        return false;
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isModerator() {
        return hasRole("MODERATOR");
    }

    public boolean isCreator() {
        return hasRole("CREATOR");
    }
}
