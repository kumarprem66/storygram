package com.storyverse.identity.domain;

import com.storyverse.identity.domain.event.*;
import com.storyverse.identity.domain.vo.*;
import com.storyverse.shared.domain.BaseAggregateRoot;
import com.storyverse.shared.domain.event.EventMetadata;

import java.time.Instant;
import java.util.*;

/**
 * User aggregate root. Owns identity lifecycle and authentication state.
 */
public class User extends BaseAggregateRoot {

    private Email email;
    private Username username;
    private HashedPassword password;
    private String displayName;
    private AccountStatus status;
    private Set<Role> roles;
    private boolean emailVerified;
    private boolean mfaEnabled;
    private int failedLoginAttempts;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Instant updatedAt;

    // Private constructor for reconstitution from persistence
    private User(UUID id) {
        super(id);
        this.roles = EnumSet.noneOf(Role.class);
    }

    /**
     * Factory method for creating a new user during registration.
     */
    public static User register(UUID id, Email email, Username username, HashedPassword password, String displayName) {
        User user = new User(id);
        user.email = email;
        user.username = username;
        user.password = password;
        user.displayName = displayName;
        user.status = AccountStatus.PENDING_VERIFICATION;
        user.roles = EnumSet.of(Role.READER);
        user.emailVerified = false;
        user.mfaEnabled = false;
        user.failedLoginAttempts = 0;
        user.createdAt = Instant.now();
        user.updatedAt = Instant.now();

        user.registerEvent(new UserRegisteredEvent(id, email.getValue(), username.getValue(),
                EventMetadata.createNew("system")));

        return user;
    }

    /**
     * Reconstitution factory for loading from persistence.
     */
    public static User reconstitute(UUID id, Email email, Username username, HashedPassword password,
                                     String displayName, AccountStatus status, Set<Role> roles,
                                     boolean emailVerified, boolean mfaEnabled, int failedLoginAttempts,
                                     Instant lastLoginAt, Instant createdAt, Instant updatedAt) {
        User user = new User(id);
        user.email = email;
        user.username = username;
        user.password = password;
        user.displayName = displayName;
        user.status = status;
        user.roles = roles != null ? EnumSet.copyOf(roles) : EnumSet.noneOf(Role.class);
        user.emailVerified = emailVerified;
        user.mfaEnabled = mfaEnabled;
        user.failedLoginAttempts = failedLoginAttempts;
        user.lastLoginAt = lastLoginAt;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        return user;
    }

    public void verifyEmail() {
        if (this.emailVerified) {
            return;
        }
        this.emailVerified = true;
        this.status = AccountStatus.ACTIVE;
        this.updatedAt = Instant.now();
        registerEvent(new UserVerifiedEvent(getId(), email.getValue(), EventMetadata.createNew("system")));
    }

    public void recordSuccessfulLogin() {
        this.failedLoginAttempts = 0;
        this.lastLoginAt = Instant.now();
        this.updatedAt = Instant.now();
        registerEvent(new UserLoggedInEvent(getId(), EventMetadata.createNew(username.getValue())));
    }

    public void recordFailedLogin() {
        this.failedLoginAttempts++;
        this.updatedAt = Instant.now();
        if (this.failedLoginAttempts >= 5) {
            lock();
        }
    }

    public void lock() {
        this.status = AccountStatus.LOCKED;
        this.updatedAt = Instant.now();
        registerEvent(new AccountLockedEvent(getId(), EventMetadata.createNew("system")));
    }

    public void unlock() {
        this.status = AccountStatus.ACTIVE;
        this.failedLoginAttempts = 0;
        this.updatedAt = Instant.now();
        registerEvent(new AccountUnlockedEvent(getId(), EventMetadata.createNew("system")));
    }

    public void changePassword(HashedPassword newPassword) {
        this.password = newPassword;
        this.updatedAt = Instant.now();
        registerEvent(new PasswordChangedEvent(getId(), EventMetadata.createNew(username.getValue())));
    }

    public void addRole(Role role) {
        this.roles.add(role);
        this.updatedAt = Instant.now();
        registerEvent(new RoleAssignedEvent(getId(), role.name(), EventMetadata.createNew("system")));
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        this.updatedAt = Instant.now();
    }

    public void suspend() {
        this.status = AccountStatus.SUSPENDED;
        this.updatedAt = Instant.now();
    }

    public void deactivate() {
        this.status = AccountStatus.DEACTIVATED;
        this.updatedAt = Instant.now();
    }

    public boolean isActive() {
        return this.status == AccountStatus.ACTIVE;
    }

    public boolean canLogin() {
        return this.status == AccountStatus.ACTIVE && this.emailVerified;
    }

    // Getters
    public Email getEmail() { return email; }
    public Username getUsername() { return username; }
    public HashedPassword getPassword() { return password; }
    public String getDisplayName() { return displayName; }
    public AccountStatus getStatus() { return status; }
    public Set<Role> getRoles() { return Collections.unmodifiableSet(roles); }
    public boolean isEmailVerified() { return emailVerified; }
    public boolean isMfaEnabled() { return mfaEnabled; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public Instant getLastLoginAt() { return lastLoginAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
