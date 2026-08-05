package com.storyverse.identity.adapters.persistence;

import com.storyverse.identity.domain.User;
import com.storyverse.identity.domain.vo.*;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maps between User domain aggregate and UserJpaEntity.
 * Handles value object conversion.
 */
final class UserMapper {

    private UserMapper() {
    }

    static User toDomain(UserJpaEntity entity) {
        Set<Role> roles = entity.getRoles().stream()
                .map(Role::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Role.class)));

        return User.reconstitute(
                entity.getId(),
                Email.of(entity.getEmail()),
                Username.of(entity.getUsername()),
                HashedPassword.of(entity.getPasswordHash()),
                entity.getDisplayName(),
                AccountStatus.valueOf(entity.getStatus()),
                roles,
                entity.isEmailVerified(),
                entity.isMfaEnabled(),
                entity.getFailedLoginAttempts(),
                entity.getLastLoginAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    static UserJpaEntity toEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
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
        return entity;
    }
}
