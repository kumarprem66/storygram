package com.storyverse.notification.application;

import com.storyverse.notification.domain.Notification;
import com.storyverse.notification.ports.NotificationRepository;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class MarkNotificationReadUseCase {
    private final NotificationRepository repository;
    private final SecurityContext securityContext;

    public MarkNotificationReadUseCase(NotificationRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        notification.markRead();
        repository.save(notification);
    }
}
