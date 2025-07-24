package com.ecommerce.backend.repositories;

import com.ecommerce.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
}
