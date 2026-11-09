package com.example.subscriptionai.controller;



import com.example.subscriptionai.dto.SubscriptionRegistrationRequest;
import com.example.subscriptionai.service.SubscriptionService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(
            SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerSubscription(
            @Valid @RequestBody SubscriptionRegistrationRequest request) {

        subscriptionService.registerSubscription(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Subscription registered successfully");
    }
}

