package com.storyverse.identity.domain;

import com.storyverse.identity.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User createUser() {
        return User.register(UUID.randomUUID(),
                Email.of("test@example.com"),
                Username.of("testuser"),
                HashedPassword.of("$2a$12$hashedpassword"),
                "Test User");
    }

    @Nested
    @DisplayName("User Registration")
    class Registration {

        @Test @DisplayName("Should create user with PENDING_VERIFICATION status")
        void shouldCreatePending() {
            User user = createUser();
            assertEquals(AccountStatus.PENDING_VERIFICATION, user.getStatus());
            assertFalse(user.isEmailVerified());
            assertFalse(user.canLogin());
        }

        @Test @DisplayName("Should assign READER role by default")
        void shouldAssignReaderRole() {
            User user = createUser();
            assertTrue(user.getRoles().contains(Role.READER));
            assertEquals(1, user.getRoles().size());
        }

        @Test @DisplayName("Should emit UserRegistered event")
        void shouldEmitEvent() {
            User user = createUser();
            assertEquals(1, user.getDomainEvents().size());
            assertEquals("UserRegistered", user.getDomainEvents().get(0).getEventType());
        }
    }

    @Nested
    @DisplayName("Email Verification")
    class Verification {

        @Test @DisplayName("Should activate account on verify")
        void shouldActivate() {
            User user = createUser();
            user.clearDomainEvents();
            user.verifyEmail();

            assertTrue(user.isEmailVerified());
            assertEquals(AccountStatus.ACTIVE, user.getStatus());
            assertTrue(user.canLogin());
            assertEquals(1, user.getDomainEvents().size());
        }

        @Test @DisplayName("Should be idempotent")
        void shouldBeIdempotent() {
            User user = createUser();
            user.verifyEmail();
            user.clearDomainEvents();

            user.verifyEmail(); // no error, no new event
            assertEquals(0, user.getDomainEvents().size());
        }
    }

    @Nested
    @DisplayName("Login Tracking")
    class LoginTracking {

        @Test @DisplayName("Should record successful login")
        void shouldRecordSuccess() {
            User user = createUser();
            user.verifyEmail();
            user.clearDomainEvents();

            user.recordSuccessfulLogin();

            assertEquals(0, user.getFailedLoginAttempts());
            assertNotNull(user.getLastLoginAt());
            assertEquals(1, user.getDomainEvents().size());
        }

        @Test @DisplayName("Should increment failed attempts")
        void shouldIncrementFailed() {
            User user = createUser();
            user.verifyEmail();

            user.recordFailedLogin();
            user.recordFailedLogin();

            assertEquals(2, user.getFailedLoginAttempts());
            assertTrue(user.isActive());
        }

        @Test @DisplayName("Should lock account after 5 failed attempts")
        void shouldLockAfterFiveFailures() {
            User user = createUser();
            user.verifyEmail();

            for (int i = 0; i < 5; i++) {
                user.recordFailedLogin();
            }

            assertEquals(AccountStatus.LOCKED, user.getStatus());
            assertFalse(user.canLogin());
        }

        @Test @DisplayName("Should reset failed attempts on success")
        void shouldResetOnSuccess() {
            User user = createUser();
            user.verifyEmail();
            user.recordFailedLogin();
            user.recordFailedLogin();

            user.recordSuccessfulLogin();

            assertEquals(0, user.getFailedLoginAttempts());
        }
    }

    @Nested
    @DisplayName("Account Locking")
    class Locking {

        @Test @DisplayName("Should unlock account")
        void shouldUnlock() {
            User user = createUser();
            user.verifyEmail();
            user.lock();

            user.unlock();

            assertEquals(AccountStatus.ACTIVE, user.getStatus());
            assertEquals(0, user.getFailedLoginAttempts());
            assertTrue(user.canLogin());
        }
    }

    @Nested
    @DisplayName("Password Change")
    class PasswordChange {

        @Test @DisplayName("Should change password and emit event")
        void shouldChangePassword() {
            User user = createUser();
            user.clearDomainEvents();
            HashedPassword newPass = HashedPassword.of("$2a$12$newhashedpassword");

            user.changePassword(newPass);

            assertEquals(newPass, user.getPassword());
            assertEquals(1, user.getDomainEvents().size());
            assertEquals("PasswordChanged", user.getDomainEvents().get(0).getEventType());
        }
    }

    @Nested
    @DisplayName("Roles")
    class Roles {

        @Test @DisplayName("Should add role")
        void shouldAddRole() {
            User user = createUser();
            user.clearDomainEvents();

            user.addRole(Role.CREATOR);

            assertTrue(user.getRoles().contains(Role.CREATOR));
            assertEquals(1, user.getDomainEvents().size());
            assertEquals("RoleAssigned", user.getDomainEvents().get(0).getEventType());
        }

        @Test @DisplayName("Should remove role")
        void shouldRemoveRole() {
            User user = createUser();
            user.addRole(Role.CREATOR);

            user.removeRole(Role.CREATOR);

            assertFalse(user.getRoles().contains(Role.CREATOR));
        }
    }
}
