package com.example.subscriptionai.repository;

import com.example.subscriptionai.entity.Customer;
import com.example.subscriptionai.entity.NotificationLog;
import com.example.subscriptionai.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    boolean existsBySubscriptionIdAndNotificationTypeAndCreatedAtBetween(
            Long subscriptionId,
            NotificationType notificationType,
            LocalDateTime start,
            LocalDateTime end
    );
}




