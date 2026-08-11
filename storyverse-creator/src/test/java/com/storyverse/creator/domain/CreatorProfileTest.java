package com.storyverse.creator.domain;

import com.storyverse.creator.domain.vo.CreatorStatus;
import com.storyverse.creator.domain.vo.VerificationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreatorProfileTest {

    private CreatorProfile createProfile() {
        return CreatorProfile.create(UUID.randomUUID(), UUID.randomUUID(), "Test Creator");
    }

    @Nested @DisplayName("Creation")
    class Creation {
        @Test @DisplayName("Should create with ACTIVE status and UNVERIFIED verification")
        void shouldCreateActive() {
            CreatorProfile p = createProfile();
            assertEquals(CreatorStatus.ACTIVE, p.getStatus());
            assertEquals(VerificationStatus.UNVERIFIED, p.getVerificationStatus());
            assertEquals(0, p.getFollowerCount());
            assertEquals(0, p.getStoryCount());
            assertTrue(p.isActive());
        }

        @Test @DisplayName("Should emit CreatorRegistered event")
        void shouldEmitEvent() {
            CreatorProfile p = createProfile();
            assertEquals(1, p.getDomainEvents().size());
            assertEquals("CreatorRegistered", p.getDomainEvents().get(0).getEventType());
        }

        @Test @DisplayName("Should set correct ownership")
        void shouldSetOwnership() {
            UUID userId = UUID.randomUUID();
            CreatorProfile p = CreatorProfile.create(UUID.randomUUID(), userId, "Writer");
            assertTrue(p.isOwnedBy(userId));
            assertFalse(p.isOwnedBy(UUID.randomUUID()));
        }
    }

    @Nested @DisplayName("Profile Update")
    class Update {
        @Test @DisplayName("Should update all profile fields")
        void shouldUpdateProfile() {
            CreatorProfile p = createProfile();
            p.clearDomainEvents();
            p.updateProfile("New Name", "Bio", "https://web.com", "en",
                    Set.of("FANTASY"), Set.of("Pen Name"));

            assertEquals("New Name", p.getDisplayName());
            assertEquals("Bio", p.getBiography());
            assertEquals("https://web.com", p.getWebsite());
            assertEquals("en", p.getLanguage());
            assertTrue(p.getGenres().contains("FANTASY"));
            assertTrue(p.getPenNames().contains("Pen Name"));
            assertEquals(1, p.getDomainEvents().size());
            assertEquals("CreatorProfileUpdated", p.getDomainEvents().get(0).getEventType());
        }

        @Test @DisplayName("Should handle null genres and penNames")
        void shouldHandleNulls() {
            CreatorProfile p = createProfile();
            p.updateProfile("Name", null, null, null, null, null);
            assertTrue(p.getGenres().isEmpty());
            assertTrue(p.getPenNames().isEmpty());
        }
    }

    @Nested @DisplayName("Verification")
    class Verification {
        @Test @DisplayName("Should verify creator")
        void shouldVerify() {
            CreatorProfile p = createProfile();
            p.clearDomainEvents();
            p.verify();

            assertEquals(CreatorStatus.VERIFIED, p.getStatus());
            assertEquals(VerificationStatus.VERIFIED, p.getVerificationStatus());
            assertTrue(p.isActive());
            assertEquals(1, p.getDomainEvents().size());
            assertEquals("CreatorVerified", p.getDomainEvents().get(0).getEventType());
        }
    }

    @Nested @DisplayName("Suspension")
    class Suspension {
        @Test @DisplayName("Should suspend creator")
        void shouldSuspend() {
            CreatorProfile p = createProfile();
            p.suspend();
            assertEquals(CreatorStatus.SUSPENDED, p.getStatus());
            assertFalse(p.isActive());
        }
    }

    @Nested @DisplayName("Counters")
    class Counters {
        @Test @DisplayName("Should increment and decrement followers")
        void shouldManageFollowers() {
            CreatorProfile p = createProfile();
            p.incrementFollowers();
            p.incrementFollowers();
            assertEquals(2, p.getFollowerCount());
            p.decrementFollowers();
            assertEquals(1, p.getFollowerCount());
        }

        @Test @DisplayName("Should not go below zero")
        void shouldNotGoBelowZero() {
            CreatorProfile p = createProfile();
            p.decrementFollowers();
            assertEquals(0, p.getFollowerCount());
            p.decrementStoryCount();
            assertEquals(0, p.getStoryCount());
        }

        @Test @DisplayName("Should manage story count")
        void shouldManageStoryCount() {
            CreatorProfile p = createProfile();
            p.incrementStoryCount();
            assertEquals(1, p.getStoryCount());
        }
    }
}
