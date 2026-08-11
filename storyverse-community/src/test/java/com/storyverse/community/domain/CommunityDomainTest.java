package com.storyverse.community.domain;

import com.storyverse.community.domain.vo.ReactionType;
import com.storyverse.community.domain.vo.TargetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommunityDomainTest {

    @Nested @DisplayName("Comment")
    class CommentTests {
        @Test @DisplayName("Should create a top-level comment")
        void shouldCreateComment() {
            Comment c = Comment.create(UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), null, "Great story!");
            assertFalse(c.isDeleted());
            assertFalse(c.isReply());
            assertEquals("Great story!", c.getContent());
        }

        @Test @DisplayName("Should create a reply")
        void shouldCreateReply() {
            UUID parentId = UUID.randomUUID();
            Comment c = Comment.create(UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), parentId, "I agree!");
            assertTrue(c.isReply());
            assertEquals(parentId, c.getParentCommentId());
        }

        @Test @DisplayName("Should edit comment content")
        void shouldEdit() {
            Comment c = Comment.create(UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), null, "Original");
            c.edit("Updated content");
            assertEquals("Updated content", c.getContent());
        }

        @Test @DisplayName("Should soft delete")
        void shouldSoftDelete() {
            Comment c = Comment.create(UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), null, "To be deleted");
            c.softDelete();
            assertTrue(c.isDeleted());
            assertEquals("[deleted]", c.getContent());
        }

        @Test @DisplayName("Should check ownership")
        void shouldCheckOwnership() {
            UUID userId = UUID.randomUUID();
            Comment c = Comment.create(UUID.randomUUID(), userId,
                    UUID.randomUUID(), null, "My comment");
            assertTrue(c.isOwnedBy(userId));
            assertFalse(c.isOwnedBy(UUID.randomUUID()));
        }
    }

    @Nested @DisplayName("Reaction")
    class ReactionTests {
        @Test @DisplayName("Should create reaction with correct types")
        void shouldCreate() {
            Reaction r = Reaction.create(UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), TargetType.STORY, ReactionType.LOVE);
            assertEquals(TargetType.STORY, r.getTargetType());
            assertEquals(ReactionType.LOVE, r.getReactionType());
        }
    }

    @Nested @DisplayName("Follow")
    class FollowTests {
        @Test @DisplayName("Should create follow relationship")
        void shouldCreate() {
            UUID follower = UUID.randomUUID();
            UUID following = UUID.randomUUID();
            Follow f = Follow.create(UUID.randomUUID(), follower, following);
            assertEquals(follower, f.getFollowerId());
            assertEquals(following, f.getFollowingId());
        }
    }

    @Nested @DisplayName("Rating")
    class RatingTests {
        @Test @DisplayName("Should create rating with valid score")
        void shouldCreateValid() {
            Rating r = Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 4);
            assertEquals(4, r.getScore());
        }

        @Test @DisplayName("Should reject score below 1")
        void shouldRejectBelowOne() {
            assertThrows(IllegalArgumentException.class,
                    () -> Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 0));
        }

        @Test @DisplayName("Should reject score above 5")
        void shouldRejectAboveFive() {
            assertThrows(IllegalArgumentException.class,
                    () -> Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 6));
        }

        @Test @DisplayName("Should update score")
        void shouldUpdateScore() {
            Rating r = Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 3);
            r.updateScore(5);
            assertEquals(5, r.getScore());
        }

        @Test @DisplayName("Should reject invalid update score")
        void shouldRejectInvalidUpdate() {
            Rating r = Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 3);
            assertThrows(IllegalArgumentException.class, () -> r.updateScore(0));
            assertThrows(IllegalArgumentException.class, () -> r.updateScore(6));
        }

        @Test @DisplayName("Should accept boundary values (1 and 5)")
        void shouldAcceptBoundaries() {
            assertDoesNotThrow(() -> Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 1));
            assertDoesNotThrow(() -> Rating.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 5));
        }
    }
}
