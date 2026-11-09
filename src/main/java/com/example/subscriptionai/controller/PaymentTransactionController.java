package com.example.subscriptionai.controller;


import com.example.subscriptionai.dto.PaymentRequest;
import com.example.subscriptionai.service.PaymentTransactionService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentService;

    public PaymentTransactionController(
            PaymentTransactionService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/record")
    public ResponseEntity<String> recordPayment(
            @Valid @RequestBody PaymentRequest request) {

        paymentService.createTransaction(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Payment transaction recorded successfully");
    }
}

