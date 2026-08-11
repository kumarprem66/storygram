package com.storyverse.notification.domain;

import com.storyverse.notification.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    private Notification createNotification() {
        return Notification.create(UUID.randomUUID(), UUID.randomUUID(),
                NotificationType.NEW_FOLLOWER, NotificationChannel.IN_APP,
                "New Follower", "User X started following you", "/profile/x");
    }

    @Nested @DisplayName("Creation")
    class Creation {
        @Test @DisplayName("Should create with QUEUED status")
        void shouldCreateQueued() {
            Notification n = createNotification();
            assertEquals(NotificationStatus.QUEUED, n.getStatus());
            assertEquals(0, n.getRetryCount());
            assertNull(n.getSentAt());
            assertNull(n.getReadAt());
            assertNotNull(n.getCreatedAt());
        }
    }

    @Nested @DisplayName("Lifecycle")
    class Lifecycle {
        @Test @DisplayName("Should mark as sent")
        void shouldMarkSent() {
            Notification n = createNotification();
            n.markSent();
            assertEquals(NotificationStatus.SENT, n.getStatus());
            assertNotNull(n.getSentAt());
        }

        @Test @DisplayName("Should mark as delivered")
        void shouldMarkDelivered() {
            Notification n = createNotification();
            n.markSent();
            n.markDelivered();
            assertEquals(NotificationStatus.DELIVERED, n.getStatus());
        }

        @Test @DisplayName("Should mark as read")
        void shouldMarkRead() {
            Notification n = createNotification();
            n.markSent();
            n.markRead();
            assertEquals(NotificationStatus.READ, n.getStatus());
            assertNotNull(n.getReadAt());
        }

        @Test @DisplayName("Should mark as failed and increment retry")
        void shouldMarkFailed() {
            Notification n = createNotification();
            n.markFailed();
            assertEquals(NotificationStatus.FAILED, n.getStatus());
            assertEquals(1, n.getRetryCount());
            assertTrue(n.canRetry());
        }

        @Test @DisplayName("Should not retry after 3 failures")
        void shouldNotRetryAfterThree() {
            Notification n = createNotification();
            n.markFailed();
            n.markFailed();
            n.markFailed();
            assertEquals(3, n.getRetryCount());
            assertFalse(n.canRetry());
        }
    }
}
