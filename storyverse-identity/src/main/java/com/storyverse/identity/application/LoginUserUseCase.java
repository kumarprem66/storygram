package com.storyverse.identity.application;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.Email;
import com.storyverse.identity.ports.PasswordEncoder;
import com.storyverse.identity.ports.TokenProvider;
import com.storyverse.identity.ports.UserRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.AuthenticationException;
import com.storyverse.shared.domain.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case: Authenticate a user with email and password.
 */
@Service
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final EventPublisher eventPublisher;

    public LoginUserUseCase(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            TokenProvider tokenProvider,
                            EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public AuthTokenResponse execute(String email, String rawPassword) {
        Email emailVo = Email.of(email);

        User user = userRepository.findByEmail(emailVo)
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!user.canLogin()) {
            throw new BusinessRuleViolationException("ACCOUNT_NOT_ACTIVE",
                    "Account is not active. Current status: " + user.getStatus());
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            user.recordFailedLogin();
            userRepository.save(user);
            eventPublisher.publishAll(user.getDomainEvents());
            user.clearDomainEvents();
            throw new AuthenticationException("Invalid email or password");
        }

        user.recordSuccessfulLogin();
        userRepository.save(user);
        eventPublisher.publishAll(user.getDomainEvents());
        user.clearDomainEvents();

        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        return new AuthTokenResponse(accessToken, refreshToken);
    }
}
