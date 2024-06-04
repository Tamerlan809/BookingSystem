package com.example.bookingsystem.services.impl;

import lombok.Getter;
import com.example.bookingsystem.security.User;
import com.example.bookingsystem.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final Map<String, PasswordResetRequest> tokenStore = new HashMap<>();
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(EmailService emailService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean initiatePasswordReset(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            String token = generateResetToken();
            tokenStore.put(token, new PasswordResetRequest(username));
            String resetLink = "http://localhost:8080/reset?token=" + token;
            emailService.sendPasswordResetEmail(username, resetLink);
            return true;
        } else {
            return false;
        }
    }

    public boolean resetPassword(String token, String newPassword) {
        PasswordResetRequest request = tokenStore.get(token);
        if (request != null && !request.isExpired()) {
            User user = userRepository.getUserByUsername(request.getUsername());
            if (user != null) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                tokenStore.remove(token);
                return true;
            }
        }
        return false;
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    private static class PasswordResetRequest {
        @Getter
        private final String username;
        private final long expiryTime;

        public PasswordResetRequest(String username) {
            this.username = username;
            this.expiryTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
