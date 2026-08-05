package com.storyverse.notification.adapters;

import com.storyverse.notification.domain.Notification;
import com.storyverse.notification.ports.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Dev/test notification sender that logs instead of sending.
 * Production replaces with email/push/SMS providers.
 */
@Component
public class LoggingNotificationSender implements NotificationSender {
    private static final Logger log = LoggerFactory.getLogger(LoggingNotificationSender.class);

    @Override
    public void send(Notification notification) {
        log.info("[NOTIFICATION] channel={} type={} recipient={} title='{}'",
                notification.getChannel(), notification.getType(), notification.getRecipientId(), notification.getTitle());
    }
}
