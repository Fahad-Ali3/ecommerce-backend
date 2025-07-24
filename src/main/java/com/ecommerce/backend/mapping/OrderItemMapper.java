package com.ecommerce.backend.mapping;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.OrderRepo;
import com.ecommerce.backend.repositories.ProductRepo;
import com.ecommerce.backend.dto.OrderItemDTO;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    // Convert OrderItem entity to OrderItemDTO
    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO item=new OrderItemDTO();

        if(orderItem.getOrder().getOrderId()!=null) {
            item.setOrderId(orderItem.getOrder().getOrderId());
        }

        item.setOrderItemId(orderItem.getOrderItemId());
        item.setQuantity(orderItem.getQuantity());
        item.setPrice(orderItem.getPrice());
        item.setProductName(orderItem.getProduct().getProductName());
        item.setProductId(orderItem.getProduct().getProductId());
        return item;
    }

    // Convert OrderItemDTO to OrderItem entity
    // Convert OrderItemDTO to OrderItem entity
    public OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO) {
        Product product = productRepo.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", orderItemDTO.getProductId(), "Product-ID"));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setOrderItemId(orderItemDTO.getOrderItemId());

        return orderItem;
    }

}
