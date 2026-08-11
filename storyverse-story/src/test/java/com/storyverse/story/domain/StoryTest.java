package com.storyverse.story.domain;

import com.storyverse.shared.domain.exception.BusinessRuleViolationException;
import com.storyverse.story.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StoryTest {

    private final UUID authorId = UUID.randomUUID();

    private Story createValidStory() {
        return Story.create(UUID.randomUUID(), authorId, "Test Title",
                "A test description", Genre.FANTASY, "en");
    }

    @Nested
    @DisplayName("Story Creation")
    class Creation {

        @Test
        @DisplayName("Should create story with DRAFT status and PRIVATE visibility")
        void shouldCreateWithDraftStatus() {
            Story story = createValidStory();

            assertEquals(StoryStatus.DRAFT, story.getStatus());
            assertEquals(Visibility.PRIVATE, story.getVisibility());
            assertEquals(AgeRating.EVERYONE, story.getAgeRating());
            assertEquals(0, story.getTotalWordCount().getValue());
            assertNull(story.getPublishedAt());
            assertNotNull(story.getCreatedAt());
        }

        @Test
        @DisplayName("Should emit StoryCreated event on creation")
        void shouldEmitCreatedEvent() {
            Story story = createValidStory();

            assertEquals(1, story.getDomainEvents().size());
            assertEquals("StoryCreated", story.getDomainEvents().get(0).getEventType());
        }

        @Test
        @DisplayName("Should set correct author ID")
        void shouldSetAuthorId() {
            Story story = createValidStory();
            assertEquals(authorId, story.getAuthorId());
            assertTrue(story.isOwnedBy(authorId));
            assertFalse(story.isOwnedBy(UUID.randomUUID()));
        }
    }

    @Nested
    @DisplayName("Story Publishing")
    class Publishing {

        @Test
        @DisplayName("Should publish story with valid metadata")
        void shouldPublishSuccessfully() {
            Story story = createValidStory();
            story.clearDomainEvents();

            story.publish();

            assertEquals(StoryStatus.PUBLISHED, story.getStatus());
            assertEquals(Visibility.PUBLIC, story.getVisibility());
            assertNotNull(story.getPublishedAt());
            assertEquals(1, story.getDomainEvents().size());
            assertEquals("StoryPublished", story.getDomainEvents().get(0).getEventType());
        }

        @Test
        @DisplayName("Should fail to publish without title")
        void shouldFailWithoutTitle() {
            Story story = Story.reconstitute(UUID.randomUUID(), authorId,
                    null, null, "description", Genre.FANTASY, null,
                    "en", AgeRating.EVERYONE, Visibility.PRIVATE,
                    StoryStatus.DRAFT, null, WordCount.zero(),
                    null, null, null);

            assertThrows(BusinessRuleViolationException.class, story::publish);
        }

        @Test
        @DisplayName("Should fail to publish with blank title")
        void shouldFailWithBlankTitle() {
            Story story = Story.reconstitute(UUID.randomUUID(), authorId,
                    "  ", null, "description", Genre.FANTASY, null,
                    "en", AgeRating.EVERYONE, Visibility.PRIVATE,
                    StoryStatus.DRAFT, null, WordCount.zero(),
                    null, null, null);

            assertThrows(BusinessRuleViolationException.class, story::publish);
        }

        @Test
        @DisplayName("Should fail to publish without description")
        void shouldFailWithoutDescription() {
            Story story = Story.reconstitute(UUID.randomUUID(), authorId,
                    "Title", null, null, Genre.FANTASY, null,
                    "en", AgeRating.EVERYONE, Visibility.PRIVATE,
                    StoryStatus.DRAFT, null, WordCount.zero(),
                    null, null, null);

            assertThrows(BusinessRuleViolationException.class, story::publish);
        }

        @Test
        @DisplayName("Should be idempotent - no error if already published")
        void shouldBeIdempotent() {
            Story story = createValidStory();
            story.publish();
            story.clearDomainEvents();

            story.publish(); // Should not throw

            assertEquals(StoryStatus.PUBLISHED, story.getStatus());
            assertEquals(0, story.getDomainEvents().size()); // No new event
        }
    }

    @Nested
    @DisplayName("Story Lifecycle")
    class Lifecycle {

        @Test
        @DisplayName("Should archive story")
        void shouldArchive() {
            Story story = createValidStory();
            story.publish();
            story.clearDomainEvents();

            story.archive();

            assertEquals(StoryStatus.ARCHIVED, story.getStatus());
            assertEquals(Visibility.PRIVATE, story.getVisibility());
        }

        @Test
        @DisplayName("Should unpublish story")
        void shouldUnpublish() {
            Story story = createValidStory();
            story.publish();

            story.unpublish();

            assertEquals(StoryStatus.UNPUBLISHED, story.getStatus());
            assertEquals(Visibility.PRIVATE, story.getVisibility());
        }

        @Test
        @DisplayName("Should mark story as deleted")
        void shouldMarkDeleted() {
            Story story = createValidStory();
            story.clearDomainEvents();

            story.markDeleted();

            assertEquals(StoryStatus.DELETED, story.getStatus());
            assertEquals(1, story.getDomainEvents().size());
            assertEquals("StoryDeleted", story.getDomainEvents().get(0).getEventType());
        }
    }

    @Nested
    @DisplayName("Story Word Count")
    class WordCountTests {

        @Test
        @DisplayName("Should recalculate word count from chapters")
        void shouldRecalculateWordCount() {
            Story story = createValidStory();
            Chapter ch1 = Chapter.create(UUID.randomUUID(), story.getId(),
                    "Ch1", "word word word", 0); // 3 words
            Chapter ch2 = Chapter.create(UUID.randomUUID(), story.getId(),
                    "Ch2", "one two three four five", 1); // 5 words

            story.recalculateWordCount(List.of(ch1, ch2));

            assertEquals(8, story.getTotalWordCount().getValue());
        }

        @Test
        @DisplayName("Should handle empty chapter list")
        void shouldHandleEmptyChapters() {
            Story story = createValidStory();

            story.recalculateWordCount(List.of());

            assertEquals(0, story.getTotalWordCount().getValue());
        }
    }

    @Nested
    @DisplayName("Story Metadata Update")
    class MetadataUpdate {

        @Test
        @DisplayName("Should update all metadata fields")
        void shouldUpdateMetadata() {
            Story story = createValidStory();
            story.clearDomainEvents();

            story.updateMetadata("New Title", "New Subtitle", "New Desc",
                    Genre.SCIENCE_FICTION, java.util.Set.of("sci-fi", "space"),
                    "fr", AgeRating.TEEN);

            assertEquals("New Title", story.getTitle());
            assertEquals("New Subtitle", story.getSubtitle());
            assertEquals("New Desc", story.getDescription());
            assertEquals(Genre.SCIENCE_FICTION, story.getGenre());
            assertEquals(2, story.getTags().size());
            assertEquals("fr", story.getLanguage());
            assertEquals(AgeRating.TEEN, story.getAgeRating());
            assertEquals(1, story.getDomainEvents().size());
            assertEquals("StoryUpdated", story.getDomainEvents().get(0).getEventType());
        }
    }
}
