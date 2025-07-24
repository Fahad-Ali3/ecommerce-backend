package com.ecommerce.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Product> products;
}
