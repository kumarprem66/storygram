package com.storyverse.story.domain;

import com.storyverse.shared.domain.BaseAggregateRoot;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.BusinessRuleViolationException;
import com.storyverse.story.domain.event.*;
import com.storyverse.story.domain.vo.*;

import java.time.Instant;
import java.util.*;

/**
 * Story aggregate root. Owns the complete lifecycle of a story and its chapters.
 */
public class Story extends BaseAggregateRoot {

    private UUID authorId;
    private String title;
    private String subtitle;
    private String description;
    private Genre genre;
    private Set<String> tags;
    private String language;
    private AgeRating ageRating;
    private Visibility visibility;
    private StoryStatus status;
    private String coverImageUrl;
    private WordCount totalWordCount;
    private Instant publishedAt;
    private Instant createdAt;
    private Instant updatedAt;

    private Story(UUID id) {
        super(id);
        this.tags = new HashSet<>();
        this.totalWordCount = WordCount.zero();
    }

    /**
     * Factory method for creating a new story.
     */
    public static Story create(UUID id, UUID authorId, String title, String description, Genre genre, String language) {
        Story story = new Story(id);
        story.authorId = authorId;
        story.title = title;
        story.description = description;
        story.genre = genre;
        story.language = language;
        story.ageRating = AgeRating.EVERYONE;
        story.visibility = Visibility.PRIVATE;
        story.status = StoryStatus.DRAFT;
        story.totalWordCount = WordCount.zero();
        story.createdAt = Instant.now();
        story.updatedAt = Instant.now();

        story.registerEvent(new StoryCreatedEvent(id, authorId, title, EventMetadata.createNew(authorId.toString())));
        return story;
    }

    /**
     * Reconstitution from persistence.
     */
    public static Story reconstitute(UUID id, UUID authorId, String title, String subtitle,
                                      String description, Genre genre, Set<String> tags, String language,
                                      AgeRating ageRating, Visibility visibility, StoryStatus status,
                                      String coverImageUrl, WordCount totalWordCount,
                                      Instant publishedAt, Instant createdAt, Instant updatedAt) {
        Story story = new Story(id);
        story.authorId = authorId;
        story.title = title;
        story.subtitle = subtitle;
        story.description = description;
        story.genre = genre;
        story.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
        story.language = language;
        story.ageRating = ageRating;
        story.visibility = visibility;
        story.status = status;
        story.coverImageUrl = coverImageUrl;
        story.totalWordCount = totalWordCount != null ? totalWordCount : WordCount.zero();
        story.publishedAt = publishedAt;
        story.createdAt = createdAt;
        story.updatedAt = updatedAt;
        return story;
    }

    public void updateMetadata(String title, String subtitle, String description, Genre genre,
                                Set<String> tags, String language, AgeRating ageRating) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.genre = genre;
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
        this.language = language;
        this.ageRating = ageRating;
        this.updatedAt = Instant.now();

        registerEvent(new StoryUpdatedEvent(getId(), title, EventMetadata.createNew(authorId.toString())));
    }

    public void publish() {
        if (this.status == StoryStatus.PUBLISHED) {
            return;
        }
        if (this.title == null || this.title.isBlank()) {
            throw new BusinessRuleViolationException("STORY_TITLE_REQUIRED", "Story must have a title to be published");
        }
        if (this.description == null || this.description.isBlank()) {
            throw new BusinessRuleViolationException("STORY_DESCRIPTION_REQUIRED", "Story must have a description to be published");
        }

        this.status = StoryStatus.PUBLISHED;
        this.visibility = Visibility.PUBLIC;
        this.publishedAt = Instant.now();
        this.updatedAt = Instant.now();

        registerEvent(new StoryPublishedEvent(getId(), authorId, title, EventMetadata.createNew(authorId.toString())));
    }

    public void unpublish() {
        this.status = StoryStatus.UNPUBLISHED;
        this.visibility = Visibility.PRIVATE;
        this.updatedAt = Instant.now();
    }

    public void archive() {
        this.status = StoryStatus.ARCHIVED;
        this.visibility = Visibility.PRIVATE;
        this.updatedAt = Instant.now();

        registerEvent(new StoryArchivedEvent(getId(), EventMetadata.createNew(authorId.toString())));
    }

    public void markDeleted() {
        this.status = StoryStatus.DELETED;
        this.visibility = Visibility.PRIVATE;
        this.updatedAt = Instant.now();

        registerEvent(new StoryDeletedEvent(getId(), authorId, EventMetadata.createNew(authorId.toString())));
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
        this.updatedAt = Instant.now();
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
        this.updatedAt = Instant.now();
    }

    public void recalculateWordCount(List<Chapter> chapters) {
        this.totalWordCount = chapters.stream()
                .map(Chapter::getWordCount)
                .reduce(WordCount.zero(), WordCount::add);
        this.updatedAt = Instant.now();
    }

    public boolean isOwnedBy(UUID userId) {
        return this.authorId.equals(userId);
    }

    public boolean isPublished() {
        return this.status == StoryStatus.PUBLISHED;
    }

    public boolean isDraft() {
        return this.status == StoryStatus.DRAFT;
    }

    // Getters
    public UUID getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getDescription() { return description; }
    public Genre getGenre() { return genre; }
    public Set<String> getTags() { return Collections.unmodifiableSet(tags); }
    public String getLanguage() { return language; }
    public AgeRating getAgeRating() { return ageRating; }
    public Visibility getVisibility() { return visibility; }
    public StoryStatus getStatus() { return status; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public WordCount getTotalWordCount() { return totalWordCount; }
    public Instant getPublishedAt() { return publishedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
