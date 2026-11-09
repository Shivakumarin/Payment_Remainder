package com.example.subscriptionai.service;

import com.example.subscriptionai.dto.UserRegistrationRequest;
import com.example.subscriptionai.entity.User;
import com.example.subscriptionai.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




        public User registerUser(UserRegistrationRequest request) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }

            User user = new User();

            user.setName(request.getName());
            user.setEmail(request.getEmail());

            user.setPasswordHash(
                    passwordEncoder.encode(request.getPassword())
            );

            user.setRole("BUSINESS_OWNER");

            return userRepository.save(user);
        }

    }


