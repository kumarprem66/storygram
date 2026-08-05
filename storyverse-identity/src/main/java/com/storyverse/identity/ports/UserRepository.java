package com.storyverse.identity.ports;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.Email;
import com.storyverse.identity.domain.vo.Username;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository port for User aggregate persistence.
 * Implemented by the persistence adapter.
 */
public interface UserRepository {

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(Email email);

    Optional<User> findByUsername(Username username);

    boolean existsByEmail(Email email);

    boolean existsByUsername(Username username);

    User save(User user);

    void delete(UUID id);
}
