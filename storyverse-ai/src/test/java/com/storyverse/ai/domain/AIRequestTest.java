package com.storyverse.ai.domain;

import com.storyverse.ai.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AIRequestTest {

    @Nested @DisplayName("Creation")
    class Creation {
        @Test @DisplayName("Should create with PENDING status")
        void shouldCreatePending() {
            AIRequest r = AIRequest.create(UUID.randomUUID(), UUID.randomUUID(),
                    AITaskType.SUMMARIZE, "Some long text");
            assertEquals(AIRequestStatus.PENDING, r.getStatus());
            assertNull(r.getProvider());
            assertNull(r.getOutput());
            assertNotNull(r.getCreatedAt());
        }
    }

    @Nested @DisplayName("Lifecycle")
    class Lifecycle {
        @Test @DisplayName("Should start request")
        void shouldStart() {
            AIRequest r = AIRequest.create(UUID.randomUUID(), UUID.randomUUID(),
                    AITaskType.REWRITE, "text");
            r.start(AIProvider.OPENAI);
            assertEquals(AIRequestStatus.IN_PROGRESS, r.getStatus());
            assertEquals(AIProvider.OPENAI, r.getProvider());
        }

        @Test @DisplayName("Should complete request")
        void shouldComplete() {
            AIRequest r = AIRequest.create(UUID.randomUUID(), UUID.randomUUID(),
                    AITaskType.GENERATE_TITLE, "description");
            r.start(AIProvider.GEMINI);
            r.complete("Generated Title", 50, 10, 200);
            assertEquals(AIRequestStatus.COMPLETED, r.getStatus());
            assertEquals("Generated Title", r.getOutput());
            assertEquals(50, r.getInputTokens());
            assertEquals(10, r.getOutputTokens());
            assertEquals(200, r.getLatencyMs());
            assertNotNull(r.getCompletedAt());
        }

        @Test @DisplayName("Should fail request")
        void shouldFail() {
            AIRequest r = AIRequest.create(UUID.randomUUID(), UUID.randomUUID(),
                    AITaskType.EXPAND, "text");
            r.start(AIProvider.ANTHROPIC);
            r.fail("Rate limit exceeded");
            assertEquals(AIRequestStatus.FAILED, r.getStatus());
            assertEquals("Rate limit exceeded", r.getErrorMessage());
            assertNotNull(r.getCompletedAt());
        }

        @Test @DisplayName("Should mark as cached")
        void shouldMarkCached() {
            AIRequest r = AIRequest.create(UUID.randomUUID(), UUID.randomUUID(),
                    AITaskType.SUMMARIZE, "text");
            r.markCached("cached response");
            assertEquals(AIRequestStatus.CACHED, r.getStatus());
            assertEquals("cached response", r.getOutput());
        }
    }
}
