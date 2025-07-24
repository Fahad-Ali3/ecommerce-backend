package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartDTO {

    private Long cartId; // Auto-generated, not validated

    @NotNull(message = "User ID is required")
    private Long userId; // ID of the user who owns the cart

    private String username; // Optional field for user’s name

    private LocalDateTime createdAt; // Auto-set, not validated

    @NotNull(message = "Cart items are required")
    private List<CartItemDTO> cartItems; // List of items in the cart
}
