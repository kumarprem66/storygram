package com.storyverse.notification.ports;

import com.storyverse.notification.domain.Notification;
import com.storyverse.notification.domain.vo.NotificationStatus;
import java.util.*;

public interface NotificationRepository {
    Optional<Notification> findById(UUID id);
    List<Notification> findByRecipientId(UUID recipientId);
    List<Notification> findByRecipientIdAndStatus(UUID recipientId, NotificationStatus status);
    Notification save(Notification notification);
    void delete(UUID id);
}
