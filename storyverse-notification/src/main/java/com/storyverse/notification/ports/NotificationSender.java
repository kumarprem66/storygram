package com.storyverse.notification.ports;

import com.storyverse.notification.domain.Notification;

/**
 * Port for sending notifications through different channels.
 */
public interface NotificationSender {
    void send(Notification notification);
}
