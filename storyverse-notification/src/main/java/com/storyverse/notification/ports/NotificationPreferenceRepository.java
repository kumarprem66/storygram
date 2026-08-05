package com.storyverse.notification.ports;

import com.storyverse.notification.domain.NotificationPreference;
import com.storyverse.notification.domain.vo.NotificationChannel;
import com.storyverse.notification.domain.vo.NotificationType;
import java.util.*;

public interface NotificationPreferenceRepository {
    List<NotificationPreference> findByUserId(UUID userId);
    Optional<NotificationPreference> findByUserIdAndTypeAndChannel(UUID userId, NotificationType type, NotificationChannel channel);
    NotificationPreference save(NotificationPreference preference);
}
