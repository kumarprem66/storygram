package com.storyverse.reader.domain;

import com.storyverse.reader.domain.vo.ReadingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReadingProgressTest {

    private ReadingProgress createProgress() {
        return ReadingProgress.start(UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID());
    }

    @Nested @DisplayName("Start Reading")
    class StartReading {
        @Test @DisplayName("Should start with IN_PROGRESS status")
        void shouldStartInProgress() {
            ReadingProgress rp = createProgress();
            assertEquals(ReadingStatus.IN_PROGRESS, rp.getStatus());
            assertEquals(0.0, rp.getProgressPercentage());
            assertEquals(0, rp.getTotalReadingTimeSeconds());
            assertNotNull(rp.getStartedAt());
            assertNotNull(rp.getLastReadAt());
            assertNull(rp.getCompletedAt());
        }

        @Test @DisplayName("Should emit ReadingStarted event")
        void shouldEmitEvent() {
            ReadingProgress rp = createProgress();
            assertEquals(1, rp.getDomainEvents().size());
            assertEquals("ReadingStarted", rp.getDomainEvents().get(0).getEventType());
        }

        @Test @DisplayName("Should set correct ownership")
        void shouldSetOwnership() {
            UUID userId = UUID.randomUUID();
            ReadingProgress rp = ReadingProgress.start(UUID.randomUUID(), userId,
                    UUID.randomUUID(), UUID.randomUUID());
            assertTrue(rp.isOwnedBy(userId));
            assertFalse(rp.isOwnedBy(UUID.randomUUID()));
        }
    }

    @Nested @DisplayName("Update Progress")
    class UpdateProgress {
        @Test @DisplayName("Should update chapter and percentage")
        void shouldUpdate() {
            ReadingProgress rp = createProgress();
            UUID newChapterId = UUID.randomUUID();

            rp.updateProgress(newChapterId, 2, 45.5, 300);

            assertEquals(newChapterId, rp.getCurrentChapterId());
            assertEquals(2, rp.getCurrentChapterIndex());
            assertEquals(45.5, rp.getProgressPercentage());
            assertEquals(300, rp.getTotalReadingTimeSeconds());
        }

        @Test @DisplayName("Should accumulate reading time")
        void shouldAccumulateTime() {
            ReadingProgress rp = createProgress();
            rp.updateProgress(UUID.randomUUID(), 0, 10.0, 100);
            rp.updateProgress(UUID.randomUUID(), 1, 20.0, 200);

            assertEquals(300, rp.getTotalReadingTimeSeconds());
        }
    }

    @Nested @DisplayName("Complete Story")
    class CompleteStory {
        @Test @DisplayName("Should mark as completed with 100%")
        void shouldComplete() {
            ReadingProgress rp = createProgress();
            rp.clearDomainEvents();

            rp.completeStory();

            assertEquals(ReadingStatus.COMPLETED, rp.getStatus());
            assertEquals(100.0, rp.getProgressPercentage());
            assertNotNull(rp.getCompletedAt());
            assertEquals(1, rp.getDomainEvents().size());
            assertEquals("StoryCompleted", rp.getDomainEvents().get(0).getEventType());
        }
    }

    @Nested @DisplayName("Abandon")
    class Abandon {
        @Test @DisplayName("Should mark as abandoned")
        void shouldAbandon() {
            ReadingProgress rp = createProgress();
            rp.abandon();
            assertEquals(ReadingStatus.ABANDONED, rp.getStatus());
        }
    }
}
