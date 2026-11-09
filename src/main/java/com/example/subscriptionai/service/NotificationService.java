package com.example.subscriptionai.service;


import com.example.subscriptionai.dto.NotificationRequest;
import com.example.subscriptionai.entity.NotificationLog;
import com.example.subscriptionai.entity.NotificationStatus;
import com.example.subscriptionai.entity.NotificationType;
import com.example.subscriptionai.entity.Subscription;
import com.example.subscriptionai.repository.NotificationLogRepository;
import com.example.subscriptionai.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationLogRepository notificationRepository;
    private final SubscriptionRepository subscriptionRepository;

    public NotificationService(
            NotificationLogRepository notificationRepository,
            SubscriptionRepository subscriptionRepository) {
        this.notificationRepository = notificationRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public NotificationLog createNotification(
            NotificationRequest request) {

        NotificationLog notification = new NotificationLog();

        notification.setNotificationType(
                NotificationType.valueOf(
                        request.getNotificationType().name()
                )
        );

        notification.setRecipient(request.getRecipient());
        notification.setMessage(request.getMessage());

        notification.setStatus(NotificationStatus.PENDING);

        if (request.getSubscriptionId() != null) {
            Subscription subscription = subscriptionRepository
                    .findById(request.getSubscriptionId())
                    .orElseThrow(() ->
                            new RuntimeException("Subscription not found"));

            notification.setSubscription(subscription);
        }

        return notificationRepository.save(notification);
    }
}

