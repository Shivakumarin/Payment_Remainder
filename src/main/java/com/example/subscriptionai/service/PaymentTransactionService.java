package com.example.subscriptionai.service;

import com.example.subscriptionai.dto.PaymentRequest;
import com.example.subscriptionai.entity.PaymentTransaction;
import com.example.subscriptionai.entity.Subscription;
import com.example.subscriptionai.repository.PaymentTransactionRepository;
import com.example.subscriptionai.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentTransactionService(
            PaymentTransactionRepository paymentRepository,
            SubscriptionRepository subscriptionRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public PaymentTransaction createTransaction(PaymentRequest request) {

        Subscription subscription = subscriptionRepository
                .findById(request.getSubscriptionId())
                .orElseThrow(() ->
                        new RuntimeException("Subscription not found"));

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setAmount(request.getAmount());

        transaction.setStatus(
                PaymentRequest.PaymentStatus.valueOf(request.getStatus().name())
        );

        transaction.setTransactionReference(
                request.getTransactionReference()
        );

        transaction.setSubscription(subscription);

        return paymentRepository.save(transaction);
    }
}

