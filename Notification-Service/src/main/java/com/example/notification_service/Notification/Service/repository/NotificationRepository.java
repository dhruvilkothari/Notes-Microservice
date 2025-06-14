package com.example.notification_service.Notification.Service.repository;

import com.example.notification_service.Notification.Service.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{
}
