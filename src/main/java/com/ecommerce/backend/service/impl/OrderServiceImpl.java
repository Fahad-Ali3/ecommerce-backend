package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.OrderItemRepo;
import com.ecommerce.backend.repositories.OrderRepo;
import com.ecommerce.backend.repositories.ProductRepo;
import com.ecommerce.backend.dto.OrderDTO;
import com.ecommerce.backend.mapping.OrderMapper;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepository;
    private final OrderMapper orderMapper;

    private final OrderItemRepo orderItemRepo;

    private final ProductRepo productRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepository, OrderMapper orderMapper, OrderItemRepo orderItemRepo, ProductRepo productRepo) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
    }

    // Get an Order by its ID
    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId, "Order-ID"));
        return orderMapper.toOrderDTO(order);  // Convert entity to DTO if found
    }

    // Get all Orders
    @Override
    public Page<OrderDTO> getAllOrders(int page, int size, String sortBy) {
        // Create a PageRequest with sorting options
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        // Get the paginated and sorted orders from the repository
        Page<Order> ordersPage = orderRepository.findAll(pageable);

        // Convert the paginated orders to DTOs
        return ordersPage.map(orderMapper::toOrderDTO);
    }



    @Override
    @Transactional
    public OrderDTO saveOrder(OrderDTO orderDTO) {

        Order order = orderMapper.toOrder(orderDTO);
        order.setCreatedAt(LocalDateTime.now());
        double totalAmount = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            item.setPrice(item.getProduct().getPrice());
            totalAmount += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalAmount);


        Order savedOrder = orderRepository.save(order);
        OrderDTO savedOrderDTO = orderMapper.toOrderDTO(savedOrder);

        for (OrderItem item : savedOrder.getOrderItems()) {
            item.setOrder(savedOrder);
        }

        orderItemRepo.saveAll(savedOrder.getOrderItems());
        return savedOrderDTO;
    }


    @Override
    @Transactional
    public OrderDTO deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId, "Order-ID"));


        orderItemRepo.deleteAll(order.getOrderItems());


        orderRepository.delete(order);

        return orderMapper.toOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId, "Order-ID"));
        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.toOrderDTO(order);
    }
}
