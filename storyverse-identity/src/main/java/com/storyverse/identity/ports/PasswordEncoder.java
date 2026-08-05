package com.storyverse.identity.ports;

import com.storyverse.identity.domain.vo.HashedPassword;

/**
 * Port for password hashing and verification.
 * Implemented by the security adapter (BCrypt).
 */
public interface PasswordEncoder {

    /**
     * Hashes a raw password.
     */
    HashedPassword encode(String rawPassword);

    /**
     * Verifies a raw password against a hashed password.
     */
    boolean matches(String rawPassword, HashedPassword hashedPassword);
}
