package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "Order ID must not be null")
    private Long orderId;

    @NotBlank(message = "Payment method must not be blank")
    private String paymentMethod; // e.g., "Stripe", "PayPal"

    @NotBlank(message = "Stripe token must not be blank")
    private String stripeToken; // Token from Stripe.js (for frontend integration)

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be a positive value")
    private Double amount; // Payment amount
}
