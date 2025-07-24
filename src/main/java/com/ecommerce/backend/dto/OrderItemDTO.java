package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDTO {

    private Long orderItemId; // Auto-generated, not to be validated

    @NotNull(message = "Order ID must not be null")
    private Long orderId;

    @NotNull(message = "Product ID must not be null")
    private Long productId;

    @NotNull(message = "Product name must not be null")
    private String productName;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive value")
    private Double price;
}
