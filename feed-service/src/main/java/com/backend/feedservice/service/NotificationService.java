package com.backend.feedservice.service;

import com.backend.feedservice.dto.NotificationsMarkRequest;
import com.backend.feedservice.entity.Notification;
import com.backend.feedservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepo;

    public void createNotification(String sender, String receiver, String type, String message){
        Notification notification = Notification
                .builder()
                .sender(sender)
                .receiver(receiver)
                .type(type)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        notificationRepo.save(notification);
    }

    public List<Notification> getUnreadNotifications(String receiver) {
        return notificationRepo.findByReceiverAndReadFalse(receiver);
    }

    public void markAsRead(NotificationsMarkRequest notificationIds) {
        notificationIds
                .getNotificationIds()
                .forEach(notificationId -> {
                    Notification notification = notificationRepo.findById(notificationId)
                            .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
                    notification.setRead(true);
                    notificationRepo.save(notification);
                });
    }
}