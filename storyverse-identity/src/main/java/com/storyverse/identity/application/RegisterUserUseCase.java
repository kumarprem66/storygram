package com.storyverse.identity.application;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.Email;
import com.storyverse.identity.domain.vo.HashedPassword;
import com.storyverse.identity.domain.vo.Username;
import com.storyverse.identity.ports.EmailVerificationPort;
import com.storyverse.identity.ports.PasswordEncoder;
import com.storyverse.identity.ports.UserRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.exception.ConflictException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case: Register a new user.
 */
@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationPort emailVerificationPort;
    private final EventPublisher eventPublisher;

    public RegisterUserUseCase(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               EmailVerificationPort emailVerificationPort,
                               EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationPort = emailVerificationPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID execute(String email, String username, String rawPassword, String displayName) {
        Email emailVo = Email.of(email);
        Username usernameVo = Username.of(username);

        if (userRepository.existsByEmail(emailVo)) {
            throw new ConflictException("EMAIL_EXISTS", "An account with this email already exists");
        }
        if (userRepository.existsByUsername(usernameVo)) {
            throw new ConflictException("USERNAME_EXISTS", "This username is already taken");
        }

        HashedPassword hashedPassword = passwordEncoder.encode(rawPassword);
        User user = User.register(UUID.randomUUID(), emailVo, usernameVo, hashedPassword, displayName);

        userRepository.save(user);
        eventPublisher.publishAll(user.getDomainEvents());
        user.clearDomainEvents();

        emailVerificationPort.generateVerificationToken(user.getId(), emailVo.getValue());

        return user.getId();
    }
}
