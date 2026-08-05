package com.storyverse.administration.domain;

import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class PlatformConfig extends BaseEntity {
    private String key;
    private String value;
    private String description;
    private Instant updatedAt;

    private PlatformConfig(UUID id) { super(id); }

    public static PlatformConfig create(UUID id, String key, String value, String description) {
        PlatformConfig c = new PlatformConfig(id);
        c.key = key; c.value = value; c.description = description; c.updatedAt = Instant.now();
        return c;
    }

    public static PlatformConfig reconstitute(UUID id, String key, String value, String description, Instant updatedAt) {
        PlatformConfig c = new PlatformConfig(id);
        c.key = key; c.value = value; c.description = description; c.updatedAt = updatedAt;
        return c;
    }

    public void updateValue(String newValue) { this.value = newValue; this.updatedAt = Instant.now(); }

    public String getKey() { return key; }
    public String getValue() { return value; }
    public String getDescription() { return description; }
    public Instant getUpdatedAt() { return updatedAt; }
}
