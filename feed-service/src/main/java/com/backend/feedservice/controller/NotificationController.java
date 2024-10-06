package com.backend.feedservice.controller;

import com.backend.feedservice.dto.NotificationsMarkRequest;
import com.backend.feedservice.entity.Notification;
import com.backend.feedservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(String username){
        return notificationService.getUnreadNotifications(username);
    }
    @PostMapping("/read/mark")
    public void markNotificationsAsRead(@RequestBody NotificationsMarkRequest notificationIds) {
        notificationService.markAsRead(notificationIds);
    }
}
