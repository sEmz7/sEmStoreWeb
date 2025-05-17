package com.example.semstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String content;

    private int likes = 0;
    private int dislikes = 0;
}

