package com.ecommerce.backend.service;


import com.ecommerce.backend.dto.CartDTO;
import org.springframework.data.domain.Page;

public interface CartService {

    CartDTO saveCart(CartDTO cartDTO);
    CartDTO deleteCart(Long cartId);
    CartDTO updateCart(CartDTO cartDTO,Long cartId);
    CartDTO getCartById(Long cartId);
    Page<CartDTO> getCarts(int page, int size, String sortBy);
}
