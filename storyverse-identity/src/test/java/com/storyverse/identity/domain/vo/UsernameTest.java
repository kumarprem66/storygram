package com.storyverse.identity.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test @DisplayName("Should create valid username")
    void shouldCreateValid() {
        Username u = Username.of("test_user_123");
        assertEquals("test_user_123", u.getValue());
    }

    @Test @DisplayName("Should reject null")
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> Username.of(null));
    }

    @Test @DisplayName("Should reject too short (< 3)")
    void shouldRejectTooShort() {
        assertThrows(IllegalArgumentException.class, () -> Username.of("ab"));
    }

    @Test @DisplayName("Should reject too long (> 30)")
    void shouldRejectTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> Username.of("a".repeat(31)));
    }

    @Test @DisplayName("Should reject special characters")
    void shouldRejectSpecialChars() {
        assertThrows(IllegalArgumentException.class, () -> Username.of("user@name"));
        assertThrows(IllegalArgumentException.class, () -> Username.of("user name"));
        assertThrows(IllegalArgumentException.class, () -> Username.of("user-name"));
    }

    @Test @DisplayName("Should have case-insensitive equality")
    void shouldBeCaseInsensitive() {
        assertEquals(Username.of("TestUser"), Username.of("testuser"));
    }

    @Test @DisplayName("Should accept min length (3)")
    void shouldAcceptMinLength() {
        assertDoesNotThrow(() -> Username.of("abc"));
    }

    @Test @DisplayName("Should accept max length (30)")
    void shouldAcceptMaxLength() {
        assertDoesNotThrow(() -> Username.of("a".repeat(30)));
    }
}
