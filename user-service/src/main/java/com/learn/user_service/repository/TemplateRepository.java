package com.learn.user_service.repository;

import com.learn.user_service.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, String> {

    // Parameter is a String userId; compare to the embedded User entity's userId field
    @Query("SELECT t FROM Template t WHERE t.user.userId = :userId")
    List<Template> findByUserId(String userId);
}
