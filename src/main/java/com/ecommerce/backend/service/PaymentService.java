package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.PaymentRequestDTO;
import com.ecommerce.backend.dto.PaymentResponseDTO;


public interface PaymentService {

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getPaymentByOrder(Long orderId);
}
