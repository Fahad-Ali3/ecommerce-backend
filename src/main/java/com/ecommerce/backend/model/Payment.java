package com.ecommerce.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Double amount;


    private String paymentMethod; // e.g., "Credit Card", "PayPal"


    private String status; // e.g., "PENDING", "SUCCESS", "FAILED"


    private String transactionId; // Unique ID from the payment gateway

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}