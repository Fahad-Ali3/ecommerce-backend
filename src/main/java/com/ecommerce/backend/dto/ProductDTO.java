package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {

    private Long productId; // No validation needed, as it’s auto-generated

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String productName;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be a positive value")
    private Integer quantity;

    private Long categoryId; // No validation, as it might be a reference

    private String categoryName; // Optional to validate based on your requirements



    private String imageUrl; // Optional, depending on your use case

    private LocalDateTime localDateTime = LocalDateTime.now(); // No validation needed
}
