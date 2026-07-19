package com.learn.user_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    String userId;

    String userName;

    String userEmail;

    String shopName;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Template> savedTemplates = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "current_template_id")
    @JsonManagedReference
    private Template currentTemplate;
}

