package com.learn.user_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long templateId;

    String templateName;

    @Lob
    String templateHtmlContent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
