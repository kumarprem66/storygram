package com.storyverse.moderation.domain;

import com.storyverse.moderation.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ModerationCaseTest {

    private ModerationCase createCase() {
        return ModerationCase.create(UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), "STORY", ReportReason.SPAM, "Looks like spam");
    }

    @Nested @DisplayName("Creation")
    class Creation {
        @Test @DisplayName("Should create with PENDING status")
        void shouldCreatePending() {
            ModerationCase c = createCase();
            assertEquals(ModerationStatus.PENDING, c.getStatus());
            assertEquals(EnforcementAction.NONE, c.getAction());
            assertEquals(ReportReason.SPAM, c.getReason());
            assertNull(c.getAssignedModeratorId());
            assertNull(c.getResolvedAt());
        }
    }

    @Nested @DisplayName("Workflow")
    class Workflow {
        @Test @DisplayName("Should assign to moderator")
        void shouldAssign() {
            ModerationCase c = createCase();
            UUID moderatorId = UUID.randomUUID();
            c.assign(moderatorId);
            assertEquals(ModerationStatus.ASSIGNED, c.getStatus());
            assertEquals(moderatorId, c.getAssignedModeratorId());
        }

        @Test @DisplayName("Should resolve with action")
        void shouldResolve() {
            ModerationCase c = createCase();
            c.resolve(EnforcementAction.WARNING, "First offense");
            assertEquals(ModerationStatus.RESOLVED, c.getStatus());
            assertEquals(EnforcementAction.WARNING, c.getAction());
            assertEquals("First offense", c.getModeratorNotes());
            assertNotNull(c.getResolvedAt());
        }

        @Test @DisplayName("Should dismiss case")
        void shouldDismiss() {
            ModerationCase c = createCase();
            c.dismiss("False report");
            assertEquals(ModerationStatus.DISMISSED, c.getStatus());
            assertEquals(EnforcementAction.NONE, c.getAction());
            assertEquals("False report", c.getModeratorNotes());
            assertNotNull(c.getResolvedAt());
        }

        @Test @DisplayName("Should escalate case")
        void shouldEscalate() {
            ModerationCase c = createCase();
            c.escalate();
            assertEquals(ModerationStatus.ESCALATED, c.getStatus());
        }

        @Test @DisplayName("Should resolve with permanent ban")
        void shouldBan() {
            ModerationCase c = createCase();
            c.resolve(EnforcementAction.PERMANENT_BAN, "Repeated violations");
            assertEquals(EnforcementAction.PERMANENT_BAN, c.getAction());
        }
    }
}
