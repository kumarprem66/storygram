package com.storyverse.creator.domain;

import com.storyverse.creator.domain.event.*;
import com.storyverse.creator.domain.vo.*;
import com.storyverse.shared.domain.BaseAggregateRoot;
import com.storyverse.shared.domain.event.EventMetadata;

import java.time.Instant;
import java.util.*;

/**
 * Creator profile aggregate root. Owns creator identity, verification, and preferences.
 */
public class CreatorProfile extends BaseAggregateRoot {

    private UUID userId;
    private String displayName;
    private String biography;
    private String avatarUrl;
    private String bannerUrl;
    private String website;
    private Set<SocialLink> socialLinks;
    private Set<String> genres;
    private Set<String> penNames;
    private String language;
    private CreatorStatus status;
    private VerificationStatus verificationStatus;
    private int followerCount;
    private int storyCount;
    private Instant createdAt;
    private Instant updatedAt;

    private CreatorProfile(UUID id) {
        super(id);
        this.socialLinks = new HashSet<>();
        this.genres = new HashSet<>();
        this.penNames = new HashSet<>();
    }

    public static CreatorProfile create(UUID id, UUID userId, String displayName) {
        CreatorProfile profile = new CreatorProfile(id);
        profile.userId = userId;
        profile.displayName = displayName;
        profile.status = CreatorStatus.ACTIVE;
        profile.verificationStatus = VerificationStatus.UNVERIFIED;
        profile.followerCount = 0;
        profile.storyCount = 0;
        profile.createdAt = Instant.now();
        profile.updatedAt = Instant.now();

        profile.registerEvent(new CreatorRegisteredEvent(id, userId, displayName,
                EventMetadata.createNew(userId.toString())));
        return profile;
    }

    public static CreatorProfile reconstitute(UUID id, UUID userId, String displayName, String biography,
                                              String avatarUrl, String bannerUrl, String website,
                                              Set<SocialLink> socialLinks, Set<String> genres,
                                              Set<String> penNames, String language, CreatorStatus status,
                                              VerificationStatus verificationStatus, int followerCount,
                                              int storyCount, Instant createdAt, Instant updatedAt) {
        CreatorProfile profile = new CreatorProfile(id);
        profile.userId = userId;
        profile.displayName = displayName;
        profile.biography = biography;
        profile.avatarUrl = avatarUrl;
        profile.bannerUrl = bannerUrl;
        profile.website = website;
        profile.socialLinks = socialLinks != null ? new HashSet<>(socialLinks) : new HashSet<>();
        profile.genres = genres != null ? new HashSet<>(genres) : new HashSet<>();
        profile.penNames = penNames != null ? new HashSet<>(penNames) : new HashSet<>();
        profile.language = language;
        profile.status = status;
        profile.verificationStatus = verificationStatus;
        profile.followerCount = followerCount;
        profile.storyCount = storyCount;
        profile.createdAt = createdAt;
        profile.updatedAt = updatedAt;
        return profile;
    }

    public void updateProfile(String displayName, String biography, String website, String language,
                              Set<String> genres, Set<String> penNames) {
        this.displayName = displayName;
        this.biography = biography;
        this.website = website;
        this.language = language;
        this.genres = genres != null ? new HashSet<>(genres) : new HashSet<>();
        this.penNames = penNames != null ? new HashSet<>(penNames) : new HashSet<>();
        this.updatedAt = Instant.now();

        registerEvent(new CreatorProfileUpdatedEvent(getId(), EventMetadata.createNew(userId.toString())));
    }

    public void verify() {
        this.verificationStatus = VerificationStatus.VERIFIED;
        this.status = CreatorStatus.VERIFIED;
        this.updatedAt = Instant.now();
        registerEvent(new CreatorVerifiedEvent(getId(), userId, EventMetadata.createNew("system")));
    }

    public void suspend() {
        this.status = CreatorStatus.SUSPENDED;
        this.updatedAt = Instant.now();
    }

    public void incrementFollowers() { this.followerCount++; }
    public void decrementFollowers() { if (this.followerCount > 0) this.followerCount--; }
    public void incrementStoryCount() { this.storyCount++; }
    public void decrementStoryCount() { if (this.storyCount > 0) this.storyCount--; }

    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; this.updatedAt = Instant.now(); }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; this.updatedAt = Instant.now(); }
    public void setSocialLinks(Set<SocialLink> socialLinks) { this.socialLinks = new HashSet<>(socialLinks); this.updatedAt = Instant.now(); }

    public boolean isOwnedBy(UUID userId) { return this.userId.equals(userId); }
    public boolean isActive() { return this.status == CreatorStatus.ACTIVE || this.status == CreatorStatus.VERIFIED; }

    // Getters
    public UUID getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public String getBiography() { return biography; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getBannerUrl() { return bannerUrl; }
    public String getWebsite() { return website; }
    public Set<SocialLink> getSocialLinks() { return Collections.unmodifiableSet(socialLinks); }
    public Set<String> getGenres() { return Collections.unmodifiableSet(genres); }
    public Set<String> getPenNames() { return Collections.unmodifiableSet(penNames); }
    public String getLanguage() { return language; }
    public CreatorStatus getStatus() { return status; }
    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public int getFollowerCount() { return followerCount; }
    public int getStoryCount() { return storyCount; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
