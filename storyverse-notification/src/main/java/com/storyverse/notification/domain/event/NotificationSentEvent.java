package com.storyverse.notification.domain.event;

import com.storyverse.shared.domain.event.BaseDomainEvent;
import com.storyverse.shared.domain.event.EventMetadata;
import java.util.UUID;

public class NotificationSentEvent extends BaseDomainEvent {
    private final UUID recipientId;
    private final String notificationType;

    public NotificationSentEvent(UUID notificationId, UUID recipientId, String notificationType, EventMetadata metadata) {
        super(notificationId, metadata);
        this.recipientId = recipientId;
        this.notificationType = notificationType;
    }

    @Override public String getEventType() { return "NotificationSent"; }
    @Override public String getAggregateType() { return "Notification"; }
    public UUID getRecipientId() { return recipientId; }
    public String getNotificationType() { return notificationType; }
}
