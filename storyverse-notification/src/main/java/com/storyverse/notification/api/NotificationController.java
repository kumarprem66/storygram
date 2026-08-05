package com.storyverse.notification.api;

import com.storyverse.notification.application.*;
import com.storyverse.notification.domain.Notification;
import com.storyverse.shared.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final GetUserNotificationsUseCase getUserNotificationsUseCase;
    private final MarkNotificationReadUseCase markNotificationReadUseCase;

    public NotificationController(GetUserNotificationsUseCase getUserNotificationsUseCase,
                                   MarkNotificationReadUseCase markNotificationReadUseCase) {
        this.getUserNotificationsUseCase = getUserNotificationsUseCase;
        this.markNotificationReadUseCase = markNotificationReadUseCase;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications() {
        List<Notification> notifications = getUserNotificationsUseCase.execute();
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> markRead(@PathVariable UUID notificationId) {
        markNotificationReadUseCase.execute(notificationId);
        return ResponseEntity.ok(ApiResponse.success(null, "Notification marked as read"));
    }
}
