package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderDTO;
import org.springframework.data.domain.Page;

public interface OrderService {

    // Method to get an Order by its ID
    OrderDTO getOrderById(Long orderId);

    // Method to get all Orders
    Page<OrderDTO> getAllOrders(int page, int size, String sortBy);

    // Method to create or update an Order
    OrderDTO saveOrder(OrderDTO orderDTO);

    // Save or update an Order


    // Method to delete an Order by its ID
    OrderDTO deleteOrder(Long orderId);

    // Method to update the status of an Order
    OrderDTO updateOrderStatus(Long orderId, String status);
}
