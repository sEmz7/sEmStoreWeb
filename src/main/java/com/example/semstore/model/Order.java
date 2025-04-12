package com.example.semstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Order {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String link;
    private String size;
    private String color;
}
