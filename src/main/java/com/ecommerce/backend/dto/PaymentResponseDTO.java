package com.ecommerce.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long paymentId;
    private Long orderId;
    private Double amount;
    private String paymentMethod;
    private String status;
    private String transactionId;
    private LocalDateTime createdAt;
}