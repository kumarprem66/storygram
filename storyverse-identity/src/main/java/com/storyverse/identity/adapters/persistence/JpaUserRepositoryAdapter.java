package com.storyverse.identity.adapters.persistence;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.Email;
import com.storyverse.identity.domain.vo.Role;
import com.storyverse.identity.domain.vo.Username;
import com.storyverse.identity.ports.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing UserRepository port using Spring Data JPA.
 */
@Component
public class JpaUserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataRepository;

    public JpaUserRepositoryAdapter(SpringDataUserRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataRepository.findByEmail(email.getValue()).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return springDataRepository.findByUsername(username.getValue()).map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataRepository.existsByEmail(email.getValue());
    }

    @Override
    public boolean existsByUsername(Username username) {
        return springDataRepository.existsByUsername(username.getValue());
    }

    @Override
    public User save(User user) {
        // Check if entity already exists (update case)
        Optional<UserJpaEntity> existing = springDataRepository.findById(user.getId());

        UserJpaEntity entity;
        if (existing.isPresent()) {
            // Update existing managed entity to preserve version for optimistic locking
            entity = existing.get();
            entity.setEmail(user.getEmail().getValue());
            entity.setUsername(user.getUsername().getValue());
            entity.setPasswordHash(user.getPassword().getValue());
            entity.setDisplayName(user.getDisplayName());
            entity.setStatus(user.getStatus().name());
            entity.setRoles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
            entity.setEmailVerified(user.isEmailVerified());
            entity.setMfaEnabled(user.isMfaEnabled());
            entity.setFailedLoginAttempts(user.getFailedLoginAttempts());
            entity.setLastLoginAt(user.getLastLoginAt());
        } else {
            // New entity (insert case)
            entity = UserMapper.toEntity(user);
        }

        UserJpaEntity saved = springDataRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        springDataRepository.deleteById(id);
    }
}
