package com.learn.user_service.repository;

import com.learn.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String userEmail);
}
