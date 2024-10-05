package com.backend.tomagram.service;

import com.backend.tomagram.models.Notification;
import com.backend.tomagram.repository.NotificationRepository;
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
}
