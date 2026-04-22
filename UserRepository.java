package com.project.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔥 Login support
    Optional<User> findByEmail(String email);

    // 🔥 Prevent duplicate registration
    boolean existsByEmail(String email);
}