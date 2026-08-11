package com.storyverse.administration.domain;

import com.storyverse.administration.domain.vo.FeatureFlagStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AdministrationDomainTest {

    @Nested @DisplayName("FeatureFlag")
    class FeatureFlagTests {
        @Test @DisplayName("Should create disabled by default")
        void shouldCreateDisabled() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "new_feature", "A new feature");
            assertEquals(FeatureFlagStatus.DISABLED, f.getStatus());
            assertEquals(0, f.getRolloutPercentage());
        }

        @Test @DisplayName("Should enable flag")
        void shouldEnable() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "feature", "desc");
            f.enable();
            assertEquals(FeatureFlagStatus.ENABLED, f.getStatus());
            assertEquals(100, f.getRolloutPercentage());
        }

        @Test @DisplayName("Should disable flag")
        void shouldDisable() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "feature", "desc");
            f.enable();
            f.disable();
            assertEquals(FeatureFlagStatus.DISABLED, f.getStatus());
            assertEquals(0, f.getRolloutPercentage());
        }

        @Test @DisplayName("Should set rollout percentage")
        void shouldSetRollout() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "feature", "desc");
            f.setRolloutPercentage(50);
            assertEquals(FeatureFlagStatus.PERCENTAGE_ROLLOUT, f.getStatus());
            assertEquals(50, f.getRolloutPercentage());
        }

        @Test @DisplayName("Should clamp rollout percentage to 0-100")
        void shouldClampPercentage() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "feature", "desc");
            f.setRolloutPercentage(150);
            assertEquals(100, f.getRolloutPercentage());
            f.setRolloutPercentage(-10);
            assertEquals(0, f.getRolloutPercentage());
        }

        @Test @DisplayName("Should be enabled for all users when ENABLED")
        void shouldBeEnabledForAll() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "feature", "desc");
            f.enable();
            assertTrue(f.isEnabledForUser(UUID.randomUUID()));
            assertTrue(f.isEnabledForUser(UUID.randomUUID()));
        }

        @Test @DisplayName("Should not be enabled when DISABLED")
        void shouldNotBeEnabledWhenDisabled() {
            FeatureFlag f = FeatureFlag.create(UUID.randomUUID(), "feature", "desc");
            assertFalse(f.isEnabledForUser(UUID.randomUUID()));
        }
    }

    @Nested @DisplayName("PlatformConfig")
    class PlatformConfigTests {
        @Test @DisplayName("Should create config entry")
        void shouldCreate() {
            PlatformConfig c = PlatformConfig.create(UUID.randomUUID(), "max_uploads", "10", "Maximum uploads");
            assertEquals("max_uploads", c.getKey());
            assertEquals("10", c.getValue());
            assertEquals("Maximum uploads", c.getDescription());
        }

        @Test @DisplayName("Should update value")
        void shouldUpdateValue() {
            PlatformConfig c = PlatformConfig.create(UUID.randomUUID(), "limit", "5", "Limit");
            c.updateValue("20");
            assertEquals("20", c.getValue());
        }
    }
}
