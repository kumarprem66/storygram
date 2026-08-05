package com.storyverse.story.adapters.persistence;

import com.storyverse.infrastructure.persistence.AuditableBaseEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "stories", indexes = {
        @Index(name = "idx_stories_author_id", columnList = "author_id"),
        @Index(name = "idx_stories_status", columnList = "status"),
        @Index(name = "idx_stories_genre", columnList = "genre")
})
public class StoryJpaEntity extends AuditableBaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "author_id", nullable = false, updatable = false)
    private UUID authorId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "subtitle", length = 200)
    private String subtitle;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "genre", length = 30)
    private String genre;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "story_tags", joinColumns = @JoinColumn(name = "story_id"))
    @Column(name = "tag", length = 50)
    private Set<String> tags = new HashSet<>();

    @Column(name = "language", length = 10)
    private String language;

    @Column(name = "age_rating", length = 20)
    private String ageRating;

    @Column(name = "visibility", nullable = false, length = 20)
    private String visibility;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "total_word_count", nullable = false)
    private int totalWordCount;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Override
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAuthorId() { return authorId; }
    public void setAuthorId(UUID authorId) { this.authorId = authorId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getAgeRating() { return ageRating; }
    public void setAgeRating(String ageRating) { this.ageRating = ageRating; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public int getTotalWordCount() { return totalWordCount; }
    public void setTotalWordCount(int totalWordCount) { this.totalWordCount = totalWordCount; }
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
}
