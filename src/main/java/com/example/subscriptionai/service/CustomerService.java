package com.example.subscriptionai.service;


import com.example.subscriptionai.dto.CustomerRegistrationRequest;
import com.example.subscriptionai.entity.Customer;
import com.example.subscriptionai.entity.User;
import com.example.subscriptionai.repository.CustomerRepository;
import com.example.subscriptionai.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public Customer registerCustomer(
            CustomerRegistrationRequest request,
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setUser(user);

        return customerRepository.save(customer);
    }
}

