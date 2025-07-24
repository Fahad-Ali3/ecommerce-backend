package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.OrderRepo;
import com.ecommerce.backend.repositories.PaymentRepo;
import com.ecommerce.backend.dto.PaymentRequestDTO;
import com.ecommerce.backend.dto.PaymentResponseDTO;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.Payment;
import com.ecommerce.backend.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey=stripeSecretKey;

    }
    @Override
    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        Order order=orderRepo.findById(paymentRequestDTO.getOrderId()).orElseThrow(()->new ResourceNotFoundException("Order",paymentRequestDTO.getOrderId(),"ORDER_ID"));
//
//        if(!order.getTotalAmount().equals(paymentRequestDTO.getAmount())){
//            throw new RuntimeException("Payment insufficient");
//        }

        try {
            ChargeCreateParams params=ChargeCreateParams.builder()
                    .setAmount((long) (paymentRequestDTO.getAmount()*100))
                    .setCurrency("usd")
                    .setSource(paymentRequestDTO.getStripeToken())
                    .setDescription("Payment for order-id: "+order.getOrderId())
                    .build();

            Charge charge=Charge.create(params);

            Payment payment=new Payment();
            payment.setOrder(order);
            payment.setAmount(paymentRequestDTO.getAmount());
            payment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
            payment.setStatus(charge.getStatus().toUpperCase());
            payment.setTransactionId(charge.getId());
            payment.setCreatedAt(LocalDateTime.now());

            Payment savedPayment=paymentRepo.save(payment);

            if ("SUCCESS".equalsIgnoreCase(savedPayment.getStatus())) {
                order.setStatus("PAID");
                orderRepo.save(order);
            }

            return convertToPaymentResponseDTO(savedPayment);
        }catch (StripeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public PaymentResponseDTO getPaymentByOrder(Long orderId) {
        Payment payment = paymentRepo.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToPaymentResponseDTO(payment);
    }

    private PaymentResponseDTO convertToPaymentResponseDTO(Payment payment) {
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(payment.getPaymentId());
        response.setOrderId(payment.getOrder().getOrderId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());
        response.setTransactionId(payment.getTransactionId());
        response.setCreatedAt(payment.getCreatedAt());
        return response;
    }
}
