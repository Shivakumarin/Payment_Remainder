package com.example.subscriptionai.repository;

import com.example.subscriptionai.entity.Customer;
import com.example.subscriptionai.entity.Subscription;
import com.example.subscriptionai.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    List<Subscription> findByStatusAndNextRenewalDate(
            SubscriptionStatus status,
            LocalDate nextRenewalDate
    );

}
