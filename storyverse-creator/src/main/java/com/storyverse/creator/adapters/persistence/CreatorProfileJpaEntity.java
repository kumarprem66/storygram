package com.storyverse.creator.adapters.persistence;

import com.storyverse.infrastructure.persistence.AuditableBaseEntity;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "creator_profiles", indexes = {
        @Index(name = "idx_creator_profiles_user_id", columnList = "user_id", unique = true)
})
public class CreatorProfileJpaEntity extends AuditableBaseEntity {
    @Id @Column(name = "id", nullable = false, updatable = false) private UUID id;
    @Column(name = "user_id", nullable = false, unique = true) private UUID userId;
    @Column(name = "display_name", nullable = false, length = 100) private String displayName;
    @Column(name = "biography", length = 2000) private String biography;
    @Column(name = "avatar_url", length = 500) private String avatarUrl;
    @Column(name = "banner_url", length = 500) private String bannerUrl;
    @Column(name = "website", length = 500) private String website;
    @Column(name = "language", length = 10) private String language;
    @Column(name = "status", nullable = false, length = 20) private String status;
    @Column(name = "verification_status", nullable = false, length = 20) private String verificationStatus;
    @Column(name = "follower_count", nullable = false) private int followerCount;
    @Column(name = "story_count", nullable = false) private int storyCount;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "creator_genres", joinColumns = @JoinColumn(name = "creator_id"))
    @Column(name = "genre", length = 50)
    private Set<String> genres = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "creator_pen_names", joinColumns = @JoinColumn(name = "creator_id"))
    @Column(name = "pen_name", length = 100)
    private Set<String> penNames = new HashSet<>();

    @Override public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String vs) { this.verificationStatus = vs; }
    public int getFollowerCount() { return followerCount; }
    public void setFollowerCount(int fc) { this.followerCount = fc; }
    public int getStoryCount() { return storyCount; }
    public void setStoryCount(int sc) { this.storyCount = sc; }
    public Set<String> getGenres() { return genres; }
    public void setGenres(Set<String> genres) { this.genres = genres; }
    public Set<String> getPenNames() { return penNames; }
    public void setPenNames(Set<String> penNames) { this.penNames = penNames; }
}
