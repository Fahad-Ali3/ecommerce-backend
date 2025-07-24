package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.OrderItemRepo;
import com.ecommerce.backend.repositories.OrderRepo;
import com.ecommerce.backend.dto.OrderItemDTO;
import com.ecommerce.backend.mapping.OrderItemMapper;
import com.ecommerce.backend.mapping.OrderMapper;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        // Convert DTO to entity
        OrderItem orderItem = orderItemMapper.toOrderItemEntity(orderItemDTO);

        // Save the entity
        OrderItem savedOrderItem = orderItemRepo.save(orderItem);
        Order order=orderItem.getOrder();
        order.getOrderItems().add(savedOrderItem);
        order=orderRepo.save(order);
        // Convert the saved entity back to DTO
        return orderItemMapper.toOrderItemDTO(savedOrderItem);
    }
    @Override
    public OrderItemDTO deleteOrderItem(Long orderItemId) {
        OrderItem orderItem=orderItemRepo.findById(orderItemId).orElseThrow(()->new ResourceNotFoundException("Order Item",orderItemId,"OrderItem-ID"));
        orderItemRepo.delete(orderItem);
        return orderItemMapper.toOrderItemDTO(orderItem);
    }

    @Override
    public OrderItemDTO getOrderItemById(Long orderItemId) {
        OrderItem orderItem=orderItemRepo.findById(orderItemId).orElseThrow(()->new ResourceNotFoundException("Order Item",orderItemId,"OrderItem-ID"));

        return orderItemMapper.toOrderItemDTO(orderItem);
    }
    @Override
    public Page<OrderItemDTO> getAllOrderItems(int page, int size, String sortBy) {
        // Create a Pageable instance with sorting and pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        // Get the paginated and sorted order items from the repository
        Page<OrderItem> orderItemsPage = orderItemRepo.findAll(pageable);

        // Convert the order items to DTOs and return the Page
        return orderItemsPage.map(orderItemMapper::toOrderItemDTO);
    }

}
