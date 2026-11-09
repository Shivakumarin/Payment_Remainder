package com.example.subscriptionai.repository;

import com.example.subscriptionai.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiInsightRepository extends JpaRepository<Customer, Long> {
}

