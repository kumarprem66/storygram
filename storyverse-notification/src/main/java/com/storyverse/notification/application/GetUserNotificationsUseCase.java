package com.storyverse.notification.application;

import com.storyverse.notification.domain.Notification;
import com.storyverse.notification.ports.NotificationRepository;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class GetUserNotificationsUseCase {
    private final NotificationRepository repository;
    private final SecurityContext securityContext;

    public GetUserNotificationsUseCase(NotificationRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
    }

    @Transactional(readOnly = true)
    public List<Notification> execute() {
        UUID userId = securityContext.requireUserId();
        return repository.findByRecipientId(userId);
    }
}
