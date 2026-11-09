package com.example.subscriptionai.controller;


import com.example.subscriptionai.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail(
            @RequestParam String recipient) {

        emailService.sendEmail(
                recipient,
                "Subscription Reminder Test",
                "Hello! Your email notification system is working."
        );

        return ResponseEntity.ok("Email sent successfully");
    }
}

