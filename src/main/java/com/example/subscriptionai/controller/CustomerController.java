package com.example.subscriptionai.controller;



import com.example.subscriptionai.dto.CustomerRegistrationRequest;
import com.example.subscriptionai.service.CustomerService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest request) {

        // Temporary development user ID.
        Long userId = 1L;

        customerService.registerCustomer(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Customer registered successfully");
    }
}

