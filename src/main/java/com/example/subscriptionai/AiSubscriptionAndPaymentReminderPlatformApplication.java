package com.example.subscriptionai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AiSubscriptionAndPaymentReminderPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiSubscriptionAndPaymentReminderPlatformApplication.class, args);
    }

}
