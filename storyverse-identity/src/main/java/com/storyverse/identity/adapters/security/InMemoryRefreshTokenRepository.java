package com.storyverse.identity.adapters.security;

import com.storyverse.identity.ports.RefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of RefreshTokenRepository.
 * Suitable for development and testing. Production should use Redis.
 */
@Component
public class InMemoryRefreshTokenRepository implements RefreshTokenRepository {

    private record TokenEntry(UUID userId, Instant expiresAt) {}

    private final Map<String, TokenEntry> tokenStore = new ConcurrentHashMap<>();

    @Override
    public void save(UUID userId, String token, long expirySeconds) {
        Instant expiresAt = Instant.now().plusSeconds(expirySeconds);
        tokenStore.put(token, new TokenEntry(userId, expiresAt));
    }

    @Override
    public Optional<UUID> findUserIdByToken(String token) {
        TokenEntry entry = tokenStore.get(token);
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
    public void delete(String token) {
        tokenStore.remove(token);
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        tokenStore.entrySet().removeIf(entry -> entry.getValue().userId().equals(userId));
    }
}
