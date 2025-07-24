package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long orderId; // Auto-generated, not to be validated

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Total amount must not be null")
    @Positive(message = "Total amount must be a positive value")
    private Double totalAmount;

    @NotNull(message = "Status must not be null")
    private String status;

    private LocalDateTime createdAt; // Set in code, no validation needed

    @NotNull(message = "Order items must not be null")
    private List<OrderItemDTO> orderItems;
}
