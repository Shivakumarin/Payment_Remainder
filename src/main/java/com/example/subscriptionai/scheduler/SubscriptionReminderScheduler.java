package com.example.subscriptionai.scheduler;



import com.example.subscriptionai.entity.NotificationType;
import com.example.subscriptionai.entity.Subscription;
import com.example.subscriptionai.entity.SubscriptionStatus;
import com.example.subscriptionai.repository.NotificationLogRepository;
import com.example.subscriptionai.repository.SubscriptionRepository;
import com.example.subscriptionai.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SubscriptionReminderScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationLogRepository notificationRepository;
    private final EmailService emailService;

    public SubscriptionReminderScheduler(
            SubscriptionRepository subscriptionRepository,
            NotificationLogRepository notificationRepository,
            EmailService emailService) {

        this.subscriptionRepository = subscriptionRepository;
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Kolkata")
    public void sendSubscriptionReminders() {

        LocalDate today = LocalDate.now();

        sendReminders(today.plusDays(3), 3);
        sendReminders(today.plusDays(1), 1);
    }

    private void sendReminders(
            LocalDate renewalDate,
            int daysRemaining) {

        List<Subscription> subscriptions =
                subscriptionRepository
                        .findByStatusAndNextRenewalDate(
                                SubscriptionStatus.ACTIVE,
                                renewalDate
                        );

        for (Subscription subscription : subscriptions) {

            Long subscriptionId = subscription.getId();

            NotificationType type = NotificationType.EMAIL;

            LocalDateTime start = LocalDate.now().atStartOfDay();
            LocalDateTime end = start.plusDays(1);

            boolean alreadySent =
                    notificationRepository
                            .existsBySubscriptionIdAndNotificationTypeAndCreatedAtBetween(
                                    subscriptionId, type, start, end);

            if (alreadySent) {
                continue;
            }

            String email = subscription.getCustomer().getEmail();

            String message =
                    "Hello "
                            + subscription.getCustomer().getName()
                            + ", your "
                            + subscription.getPlanName()
                            + " subscription of Rs. "
                            + subscription.getAmount()
                            + " will renew in "
                            + daysRemaining
                            + " day(s). Renewal date: "
                            + renewalDate;

            try {
                emailService.sendEmail(
                        email,
                        "Subscription Renewal Reminder",
                        message
                );

                System.out.println(
                        "Reminder email sent: " + email
                );

            } catch (Exception exception) {

                System.err.println(
                        "Failed to send reminder to: " + email
                );

                exception.printStackTrace();
            }
        }
    }
}

