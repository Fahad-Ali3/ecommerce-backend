package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDTO {

    private Long cartItemId; // Auto-generated, not validated

    @NotNull(message = "Product ID is required")
    private Long productId; // For request (when creating)

    private String productName; // For response (when fetching)

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;
}
