package com.storyverse.story.domain;

import com.storyverse.story.domain.vo.ChapterStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ChapterTest {

    @Test
    @DisplayName("Should create chapter with DRAFT status and calculated word count")
    void shouldCreateChapter() {
        Chapter ch = Chapter.create(UUID.randomUUID(), UUID.randomUUID(),
                "Chapter 1", "Hello world from the story", 0);

        assertEquals(ChapterStatus.DRAFT, ch.getStatus());
        assertEquals(5, ch.getWordCount().getValue());
        assertEquals(0, ch.getOrderIndex());
    }

    @Test
    @DisplayName("Should update content and recalculate word count")
    void shouldUpdateContent() {
        Chapter ch = Chapter.create(UUID.randomUUID(), UUID.randomUUID(),
                "Ch1", "one two three", 0);

        ch.updateContent("Ch1 Updated", "one two three four five six");

        assertEquals("Ch1 Updated", ch.getTitle());
        assertEquals(6, ch.getWordCount().getValue());
    }

    @Test
    @DisplayName("Should publish chapter")
    void shouldPublish() {
        Chapter ch = Chapter.create(UUID.randomUUID(), UUID.randomUUID(), "Ch1", "content", 0);

        ch.publish();

        assertEquals(ChapterStatus.PUBLISHED, ch.getStatus());
        assertTrue(ch.isPublished());
    }

    @Test
    @DisplayName("Should revert to draft")
    void shouldRevertToDraft() {
        Chapter ch = Chapter.create(UUID.randomUUID(), UUID.randomUUID(), "Ch1", "content", 0);
        ch.publish();

        ch.revertToDraft();

        assertEquals(ChapterStatus.DRAFT, ch.getStatus());
        assertFalse(ch.isPublished());
    }

    @Test
    @DisplayName("Should handle empty content")
    void shouldHandleEmptyContent() {
        Chapter ch = Chapter.create(UUID.randomUUID(), UUID.randomUUID(), "Ch1", "", 0);
        assertEquals(0, ch.getWordCount().getValue());
    }

    @Test
    @DisplayName("Should handle null content")
    void shouldHandleNullContent() {
        Chapter ch = Chapter.create(UUID.randomUUID(), UUID.randomUUID(), "Ch1", null, 0);
        assertEquals(0, ch.getWordCount().getValue());
    }
}
