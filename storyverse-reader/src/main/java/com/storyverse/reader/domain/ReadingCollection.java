package com.storyverse.reader.domain;

import com.storyverse.reader.domain.vo.CollectionVisibility;
import com.storyverse.shared.domain.BaseEntity;

import java.time.Instant;
import java.util.*;

public class ReadingCollection extends BaseEntity {

    private UUID userId;
    private String name;
    private String description;
    private CollectionVisibility visibility;
    private Set<UUID> storyIds;
    private Instant createdAt;
    private Instant updatedAt;

    private ReadingCollection(UUID id) {
        super(id);
        this.storyIds = new HashSet<>();
    }

    public static ReadingCollection create(UUID id, UUID userId, String name, String description, CollectionVisibility visibility) {
        ReadingCollection c = new ReadingCollection(id);
        c.userId = userId;
        c.name = name;
        c.description = description;
        c.visibility = visibility;
        c.createdAt = Instant.now();
        c.updatedAt = Instant.now();
        return c;
    }

    public static ReadingCollection reconstitute(UUID id, UUID userId, String name, String description,
                                                  CollectionVisibility visibility, Set<UUID> storyIds,
                                                  Instant createdAt, Instant updatedAt) {
        ReadingCollection c = new ReadingCollection(id);
        c.userId = userId;
        c.name = name;
        c.description = description;
        c.visibility = visibility;
        c.storyIds = storyIds != null ? new HashSet<>(storyIds) : new HashSet<>();
        c.createdAt = createdAt;
        c.updatedAt = updatedAt;
        return c;
    }

    public void addStory(UUID storyId) { this.storyIds.add(storyId); this.updatedAt = Instant.now(); }
    public void removeStory(UUID storyId) { this.storyIds.remove(storyId); this.updatedAt = Instant.now(); }
    public void rename(String name, String description) { this.name = name; this.description = description; this.updatedAt = Instant.now(); }
    public boolean isOwnedBy(UUID userId) { return this.userId.equals(userId); }

    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public CollectionVisibility getVisibility() { return visibility; }
    public Set<UUID> getStoryIds() { return Collections.unmodifiableSet(storyIds); }
    public int getStoryCount() { return storyIds.size(); }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
