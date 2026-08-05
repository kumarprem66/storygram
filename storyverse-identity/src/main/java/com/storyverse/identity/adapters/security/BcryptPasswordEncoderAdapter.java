package com.storyverse.identity.adapters.security;

import com.storyverse.identity.domain.vo.HashedPassword;
import com.storyverse.identity.ports.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * BCrypt implementation of the PasswordEncoder port.
 */
@Component
public class BcryptPasswordEncoderAdapter implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(12);

    @Override
    public HashedPassword encode(String rawPassword) {
        String hashed = bCryptEncoder.encode(rawPassword);
        return HashedPassword.of(hashed);
    }

    @Override
    public boolean matches(String rawPassword, HashedPassword hashedPassword) {
        return bCryptEncoder.matches(rawPassword, hashedPassword.getValue());
    }
}
