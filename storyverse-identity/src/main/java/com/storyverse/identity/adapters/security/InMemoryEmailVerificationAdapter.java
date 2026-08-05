package com.storyverse.identity.adapters.security;

import com.storyverse.identity.ports.EmailVerificationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of EmailVerificationPort.
 * Suitable for development and testing. Production should use database-backed storage.
 */
@Component
public class InMemoryEmailVerificationAdapter implements EmailVerificationPort {

    private static final Logger log = LoggerFactory.getLogger(InMemoryEmailVerificationAdapter.class);
    private static final long TOKEN_EXPIRY_HOURS = 24;

    private record VerificationEntry(UUID userId, String email, Instant expiresAt) {}

    private final Map<String, VerificationEntry> tokenStore = new ConcurrentHashMap<>();

    @Override
    public String generateVerificationToken(UUID userId, String email) {
        String token = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now().plusSeconds(TOKEN_EXPIRY_HOURS * 3600);
        tokenStore.put(token, new VerificationEntry(userId, email, expiresAt));
        log.info("Generated email verification token for user {} (token: {})", userId, token);
        return token;
    }

    @Override
    public Optional<UUID> validateToken(String token) {
        VerificationEntry entry = tokenStore.get(token);
        if (entry == null) {
            return Optional.empty();
        }
        if (Instant.now().isAfter(entry.expiresAt())) {
            tokenStore.remove(token);
            return Optional.empty();
        }
        return Optional.of(entry.userId());
    }

    @Override
    public void invalidateToken(String token) {
        tokenStore.remove(token);
    }
}
