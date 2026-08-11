package com.storyverse.identity.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test @DisplayName("Should create valid email")
    void shouldCreateValid() {
        Email email = Email.of("Test@Example.COM");
        assertEquals("test@example.com", email.getValue());
    }

    @Test @DisplayName("Should normalize to lowercase")
    void shouldNormalize() {
        Email email = Email.of("  USER@DOMAIN.COM  ");
        assertEquals("user@domain.com", email.getValue());
    }

    @Test @DisplayName("Should reject null")
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> Email.of(null));
    }

    @Test @DisplayName("Should reject blank")
    void shouldRejectBlank() {
        assertThrows(IllegalArgumentException.class, () -> Email.of("  "));
    }

    @Test @DisplayName("Should reject invalid format")
    void shouldRejectInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Email.of("not-an-email"));
        assertThrows(IllegalArgumentException.class, () -> Email.of("missing@domain"));
        assertThrows(IllegalArgumentException.class, () -> Email.of("@domain.com"));
    }

    @Test @DisplayName("Should have value equality")
    void shouldHaveValueEquality() {
        assertEquals(Email.of("a@b.com"), Email.of("A@B.COM"));
        assertNotEquals(Email.of("a@b.com"), Email.of("x@y.com"));
    }
}
