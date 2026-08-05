package com.storyverse.administration.domain;

import com.storyverse.administration.domain.vo.FeatureFlagStatus;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class FeatureFlag extends BaseEntity {
    private String name;
    private String description;
    private FeatureFlagStatus status;
    private int rolloutPercentage;
    private Instant createdAt;
    private Instant updatedAt;

    private FeatureFlag(UUID id) { super(id); }

    public static FeatureFlag create(UUID id, String name, String description) {
        FeatureFlag f = new FeatureFlag(id);
        f.name = name; f.description = description; f.status = FeatureFlagStatus.DISABLED;
        f.rolloutPercentage = 0; f.createdAt = Instant.now(); f.updatedAt = Instant.now();
        return f;
    }

    public static FeatureFlag reconstitute(UUID id, String name, String description, FeatureFlagStatus status,
                                            int rolloutPercentage, Instant createdAt, Instant updatedAt) {
        FeatureFlag f = new FeatureFlag(id);
        f.name = name; f.description = description; f.status = status;
        f.rolloutPercentage = rolloutPercentage; f.createdAt = createdAt; f.updatedAt = updatedAt;
        return f;
    }

    public void enable() { this.status = FeatureFlagStatus.ENABLED; this.rolloutPercentage = 100; this.updatedAt = Instant.now(); }
    public void disable() { this.status = FeatureFlagStatus.DISABLED; this.rolloutPercentage = 0; this.updatedAt = Instant.now(); }
    public void setRolloutPercentage(int percentage) { this.status = FeatureFlagStatus.PERCENTAGE_ROLLOUT; this.rolloutPercentage = Math.max(0, Math.min(100, percentage)); this.updatedAt = Instant.now(); }
    public boolean isEnabledForUser(UUID userId) { return status == FeatureFlagStatus.ENABLED || (status == FeatureFlagStatus.PERCENTAGE_ROLLOUT && Math.abs(userId.hashCode() % 100) < rolloutPercentage); }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public FeatureFlagStatus getStatus() { return status; }
    public int getRolloutPercentage() { return rolloutPercentage; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
