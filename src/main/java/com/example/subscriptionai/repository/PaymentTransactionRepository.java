package com.example.subscriptionai.repository;

import com.example.subscriptionai.entity.Customer;
import com.example.subscriptionai.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}

