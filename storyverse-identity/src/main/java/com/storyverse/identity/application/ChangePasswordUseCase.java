package com.storyverse.identity.application;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.HashedPassword;
import com.storyverse.identity.ports.PasswordEncoder;
import com.storyverse.identity.ports.TokenProvider;
import com.storyverse.identity.ports.UserRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.AuthenticationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case: Change the authenticated user's password.
 */
@Service
public class ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final EventPublisher eventPublisher;

    public ChangePasswordUseCase(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 TokenProvider tokenProvider,
                                 EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void execute(UUID userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AuthenticationException("Current password is incorrect");
        }

        HashedPassword newHashedPassword = passwordEncoder.encode(newPassword);
        user.changePassword(newHashedPassword);
        userRepository.save(user);

        // Revoke all existing tokens to force re-login
        tokenProvider.revokeAllUserTokens(userId);

        eventPublisher.publishAll(user.getDomainEvents());
        user.clearDomainEvents();
    }
}
