package com.storyverse.identity.adapters.security;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.Role;
import com.storyverse.identity.ports.RefreshTokenRepository;
import com.storyverse.identity.ports.TokenProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JWT-based implementation of the TokenProvider port.
 * Generates and validates access and refresh tokens.
 */
@Component
public class JwtTokenProvider implements TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final SecretKey secretKey;
    private final long accessTokenExpirySeconds;
    private final long refreshTokenExpirySeconds;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenProvider(
            @Value("${storyverse.security.jwt.secret:defaultSecretKeyThatShouldBeChangedInProduction0123456789}") String secret,
            @Value("${storyverse.security.jwt.access-token-expiry-seconds:900}") long accessTokenExpirySeconds,
            @Value("${storyverse.security.jwt.refresh-token-expiry-seconds:604800}") long refreshTokenExpirySeconds,
            RefreshTokenRepository refreshTokenRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirySeconds = accessTokenExpirySeconds;
        this.refreshTokenExpirySeconds = refreshTokenExpirySeconds;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(accessTokenExpirySeconds);

        String roles = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername().getValue())
                .claim("email", user.getEmail().getValue())
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        refreshTokenRepository.save(user.getId(), token, refreshTokenExpirySeconds);
        return token;
    }

    @Override
    public Optional<UUID> validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(UUID.fromString(claims.getSubject()));
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid access token: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UUID> validateRefreshToken(String token) {
        return refreshTokenRepository.findUserIdByToken(token);
    }

    @Override
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.delete(token);
    }

    @Override
    public void revokeAllUserTokens(UUID userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }
}
