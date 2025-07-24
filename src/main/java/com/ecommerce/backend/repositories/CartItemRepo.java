package com.ecommerce.backend.repositories;

import com.ecommerce.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
}
