package com.storyverse.identity.adapters.security;

import com.storyverse.shared.domain.exception.AuthenticationException;
import com.storyverse.shared.security.AuthenticatedUser;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Security implementation of the SecurityContext port.
 * Bridges Spring Security's authentication to the domain's AuthenticatedUser model.
 */
@Component
public class SpringSecurityContextAdapter implements SecurityContext {

    @Override
    public Optional<AuthenticatedUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof AuthenticatedUser authenticatedUser) {
            return Optional.of(authenticatedUser);
        }

        // Fallback: extract from token claims if stored as a custom principal
        return Optional.empty();
    }

    @Override
    public AuthenticatedUser requireCurrentUser() {
        return getCurrentUser()
                .orElseThrow(() -> new AuthenticationException("No authenticated user in security context"));
    }
}
