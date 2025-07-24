package com.ecommerce.backend.mapping;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.UserRepo;
import com.ecommerce.backend.dto.OrderDTO;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderItemMapper orderItemMapper;

    // Convert OrderDTO to Order
    // Convert OrderDTO to Order
    public Order toOrder(OrderDTO orderDTO) {
        User user = userRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", orderDTO.getUserId(), "User-ID"));

        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setStatus(orderDTO.getStatus());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setUser(user);

        // Correctly set the Order reference in each OrderItem
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(orderItemDTO -> {
                    OrderItem orderItem = orderItemMapper.toOrderItemEntity(orderItemDTO);
                    orderItem.setOrder(order); // Set the Order reference
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        return order;
    }

    // Convert Order to OrderDTO
    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(order.getOrderId());  // Fix: you were setting orderDTO.getOrderId() instead of order.getOrderId()
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setTotalAmount(order.getTotalAmount());

        // Map OrderItems correctly
        orderDTO.setOrderItems(order.getOrderItems().stream()
                .map(orderItemMapper::toOrderItemDTO)
                .collect(Collectors.toList()));

        return orderDTO;
    }
}
