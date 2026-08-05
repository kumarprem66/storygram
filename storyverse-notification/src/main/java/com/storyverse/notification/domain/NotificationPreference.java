package com.storyverse.notification.domain;

import com.storyverse.notification.domain.vo.NotificationChannel;
import com.storyverse.notification.domain.vo.NotificationType;
import com.storyverse.shared.domain.BaseEntity;
import java.util.UUID;

public class NotificationPreference extends BaseEntity {
    private UUID userId;
    private NotificationType type;
    private NotificationChannel channel;
    private boolean enabled;

    private NotificationPreference(UUID id) { super(id); }

    public static NotificationPreference create(UUID id, UUID userId, NotificationType type, NotificationChannel channel, boolean enabled) {
        NotificationPreference p = new NotificationPreference(id);
        p.userId = userId;
        p.type = type;
        p.channel = channel;
        p.enabled = enabled;
        return p;
    }

    public static NotificationPreference reconstitute(UUID id, UUID userId, NotificationType type, NotificationChannel channel, boolean enabled) {
        return create(id, userId, type, channel, enabled);
    }

    public void enable() { this.enabled = true; }
    public void disable() { this.enabled = false; }

    public UUID getUserId() { return userId; }
    public NotificationType getType() { return type; }
    public NotificationChannel getChannel() { return channel; }
    public boolean isEnabled() { return enabled; }
}
