package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.PaymentRequestDTO;
import com.ecommerce.backend.dto.PaymentResponseDTO;
import com.ecommerce.backend.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody @Valid PaymentRequestDTO paymentRequest) {
        PaymentResponseDTO response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentResponseDTO response = paymentService.getPaymentByOrder(orderId);
        return ResponseEntity.ok(response);
    }
}