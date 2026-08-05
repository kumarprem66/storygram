package com.storyverse.notification.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.notification.domain.Notification;
import com.storyverse.notification.domain.event.NotificationSentEvent;
import com.storyverse.notification.domain.vo.*;
import com.storyverse.notification.ports.NotificationRepository;
import com.storyverse.notification.ports.NotificationSender;
import com.storyverse.shared.domain.event.EventMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class SendNotificationUseCase {
    private static final Logger log = LoggerFactory.getLogger(SendNotificationUseCase.class);
    private final NotificationRepository repository;
    private final NotificationSender sender;
    private final EventPublisher eventPublisher;

    public SendNotificationUseCase(NotificationRepository repository, NotificationSender sender, EventPublisher eventPublisher) {
        this.repository = repository;
        this.sender = sender;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UUID execute(UUID recipientId, NotificationType type, NotificationChannel channel, String title, String body, String actionUrl) {
        Notification notification = Notification.create(UUID.randomUUID(), recipientId, type, channel, title, body, actionUrl);
        try {
            sender.send(notification);
            notification.markSent();
            eventPublisher.publish(new NotificationSentEvent(notification.getId(), recipientId, type.name(), EventMetadata.createNew("system")));
        } catch (Exception e) {
            log.error("Failed to send notification {}: {}", notification.getId(), e.getMessage());
            notification.markFailed();
        }
        repository.save(notification);
        return notification.getId();
    }
}
