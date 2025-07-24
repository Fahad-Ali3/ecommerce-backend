package com.ecommerce.backend.repositories;

import com.ecommerce.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {
}
