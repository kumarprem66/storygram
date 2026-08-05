package com.storyverse.identity.application;

import com.storyverse.identity.ports.TokenProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case: Logout user by revoking their refresh token.
 */
@Service
public class LogoutUserUseCase {

    private final TokenProvider tokenProvider;

    public LogoutUserUseCase(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public void execute(String refreshToken) {
        tokenProvider.revokeRefreshToken(refreshToken);
    }

    public void executeAll(UUID userId) {
        tokenProvider.revokeAllUserTokens(userId);
    }
}
