package com.storyverse.notification.domain;

import com.storyverse.notification.domain.vo.*;
import com.storyverse.shared.domain.BaseEntity;
import java.time.Instant;
import java.util.UUID;

public class Notification extends BaseEntity {
    private UUID recipientId;
    private NotificationType type;
    private NotificationChannel channel;
    private NotificationStatus status;
    private String title;
    private String body;
    private String actionUrl;
    private int retryCount;
    private Instant sentAt;
    private Instant readAt;
    private Instant createdAt;

    private Notification(UUID id) { super(id); }

    public static Notification create(UUID id, UUID recipientId, NotificationType type,
                                       NotificationChannel channel, String title, String body, String actionUrl) {
        Notification n = new Notification(id);
        n.recipientId = recipientId;
        n.type = type;
        n.channel = channel;
        n.status = NotificationStatus.QUEUED;
        n.title = title;
        n.body = body;
        n.actionUrl = actionUrl;
        n.retryCount = 0;
        n.createdAt = Instant.now();
        return n;
    }

    public static Notification reconstitute(UUID id, UUID recipientId, NotificationType type,
                                             NotificationChannel channel, NotificationStatus status,
                                             String title, String body, String actionUrl,
                                             int retryCount, Instant sentAt, Instant readAt, Instant createdAt) {
        Notification n = new Notification(id);
        n.recipientId = recipientId;
        n.type = type;
        n.channel = channel;
        n.status = status;
        n.title = title;
        n.body = body;
        n.actionUrl = actionUrl;
        n.retryCount = retryCount;
        n.sentAt = sentAt;
        n.readAt = readAt;
        n.createdAt = createdAt;
        return n;
    }

    public void markSent() { this.status = NotificationStatus.SENT; this.sentAt = Instant.now(); }
    public void markDelivered() { this.status = NotificationStatus.DELIVERED; }
    public void markRead() { this.status = NotificationStatus.READ; this.readAt = Instant.now(); }
    public void markFailed() { this.status = NotificationStatus.FAILED; this.retryCount++; }
    public boolean canRetry() { return this.retryCount < 3; }

    public UUID getRecipientId() { return recipientId; }
    public NotificationType getType() { return type; }
    public NotificationChannel getChannel() { return channel; }
    public NotificationStatus getStatus() { return status; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getActionUrl() { return actionUrl; }
    public int getRetryCount() { return retryCount; }
    public Instant getSentAt() { return sentAt; }
    public Instant getReadAt() { return readAt; }
    public Instant getCreatedAt() { return createdAt; }
}
