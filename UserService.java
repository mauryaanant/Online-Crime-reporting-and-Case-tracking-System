package com.project.services;

import com.project.models.User;
import com.project.models.Role;
import com.project.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService { // 🔥 renamed (important)

    @Autowired
    private UserRepository userRepository;

    // 🔹 Register User
    public User registerUser(String name, String email, String phone, String password) {

        // 🔥 Normalize email
        email = email.trim().toLowerCase();

        // 🔥 Check duplicate email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // 🔹 Find user by email
    public User findByEmail(String email) {

        if (email == null) return null;

        return userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElse(null);
    }
}