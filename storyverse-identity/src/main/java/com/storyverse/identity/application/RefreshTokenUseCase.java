package com.storyverse.identity.application;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.ports.TokenProvider;
import com.storyverse.identity.ports.UserRepository;
import com.storyverse.shared.domain.exception.AuthenticationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case: Refresh an expired access token using a valid refresh token.
 */
@Service
public class RefreshTokenUseCase {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public RefreshTokenUseCase(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public AuthTokenResponse execute(String refreshToken) {
        UUID userId = tokenProvider.validateRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException("Invalid or expired refresh token"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (!user.canLogin()) {
            tokenProvider.revokeAllUserTokens(userId);
            throw new AuthenticationException("Account is no longer active");
        }

        // Rotate refresh token
        tokenProvider.revokeRefreshToken(refreshToken);
        String newAccessToken = tokenProvider.generateAccessToken(user);
        String newRefreshToken = tokenProvider.generateRefreshToken(user);

        return new AuthTokenResponse(newAccessToken, newRefreshToken);
    }
}
