package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CartItemDTO;
import org.springframework.data.domain.Page;

public interface CartItemService {

    CartItemDTO deleteCartItem(Long cartItemId);
    Page<CartItemDTO> getAllCarts(int page, int size, String sortBy);
    CartItemDTO getCartItemById(Long cartItemId);
}
