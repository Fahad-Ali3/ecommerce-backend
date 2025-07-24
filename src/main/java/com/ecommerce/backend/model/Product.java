package com.ecommerce.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private Double price;

    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String imageUrl;

    private LocalDateTime localDateTime=LocalDateTime.now();

}
