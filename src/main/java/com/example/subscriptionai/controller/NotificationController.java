package com.example.subscriptionai.controller;


import com.example.subscriptionai.dto.NotificationRequest;
import com.example.subscriptionai.service.NotificationService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(
            @Valid @RequestBody NotificationRequest request) {

        notificationService.createNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Notification created successfully");
    }
}
