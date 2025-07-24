package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderItemDTO;
import org.springframework.data.domain.Page;

public interface OrderItemService {

    OrderItemDTO deleteOrderItem(Long orderItemId);
    OrderItemDTO getOrderItemById(Long orderItemId);
    Page<OrderItemDTO> getAllOrderItems(int page, int size, String sortBy);
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
}
