package com.example.subscriptionai.controller;


import com.example.subscriptionai.scheduler.SubscriptionReminderScheduler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.time.LocalDate;

@RestController
@RequestMapping("/api/reminders")
public class ReminderTestController {

    private final SubscriptionReminderScheduler scheduler;

    public ReminderTestController(
            SubscriptionReminderScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/test/{subscriptionId}/{days}")
    public ResponseEntity<String> testReminder(
            @PathVariable Long subscriptionId,
            @PathVariable int days) {

        if (days != 1 && days != 3) {
            return ResponseEntity.badRequest()
                    .body("Days must be 1 or 3");
        }

        // We will replace this with a subscription-specific
        // test method in the next step.

        return ResponseEntity.ok(
                "Test endpoint created. Scheduler integration pending."
        );
    }
}

