package com.storyverse.notification.adapters;

import com.storyverse.notification.domain.Notification;
import com.storyverse.notification.domain.vo.NotificationStatus;
import com.storyverse.notification.ports.NotificationRepository;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryNotificationRepository implements NotificationRepository {
    private final Map<UUID, Notification> store = new ConcurrentHashMap<>();

    @Override public Optional<Notification> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<Notification> findByRecipientId(UUID recipientId) { return store.values().stream().filter(n -> n.getRecipientId().equals(recipientId)).toList(); }
    @Override public List<Notification> findByRecipientIdAndStatus(UUID recipientId, NotificationStatus status) { return store.values().stream().filter(n -> n.getRecipientId().equals(recipientId) && n.getStatus() == status).toList(); }
    @Override public Notification save(Notification notification) { store.put(notification.getId(), notification); return notification; }
    @Override public void delete(UUID id) { store.remove(id); }
}
