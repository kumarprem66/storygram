package com.storyverse.identity.application;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.ports.EmailVerificationPort;
import com.storyverse.identity.ports.UserRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.AuthenticationException;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case: Verify a user's email address using a verification token.
 */
@Service
public class VerifyEmailUseCase {

    private final EmailVerificationPort emailVerificationPort;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public VerifyEmailUseCase(EmailVerificationPort emailVerificationPort,
                              UserRepository userRepository,
                              EventPublisher eventPublisher) {
        this.emailVerificationPort = emailVerificationPort;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void execute(String token) {
        UUID userId = emailVerificationPort.validateToken(token)
                .orElseThrow(() -> new AuthenticationException("Invalid or expired verification token"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        user.verifyEmail();
        userRepository.save(user);
        emailVerificationPort.invalidateToken(token);

        eventPublisher.publishAll(user.getDomainEvents());
        user.clearDomainEvents();
    }
}
