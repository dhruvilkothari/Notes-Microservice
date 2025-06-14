package com.example.notification_service.Notification.Service.service;

import com.example.notification_service.Notification.Service.dto.FollowDto;
import com.example.notification_service.Notification.Service.entity.NotificationEntity;
import com.example.notification_service.Notification.Service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    @KafkaListener(topics = "follow-request", groupId = "my-group-id")
    public void listen(FollowDto followDto) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setFollowerId(followDto.getFollowerId());
        notificationEntity.setMessage(followDto.getMessage());
        notificationRepository.save(notificationEntity);
        System.out.println("Received message: " + followDto);
    }

}
