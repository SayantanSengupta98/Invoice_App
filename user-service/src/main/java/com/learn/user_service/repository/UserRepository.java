package com.learn.user_service.repository;

import com.learn.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserEmail(String userEmail);

    User findByUserEmail(String userEmail);
}
