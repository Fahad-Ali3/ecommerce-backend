package com.ecommerce.backend.mapping;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.ProductRepo;
import com.ecommerce.backend.dto.CartItemDTO;
import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapping {

    @Autowired
    private ProductRepo productRepo;

    public CartItemDTO toCartItemDTO(CartItem cartItem){
        CartItemDTO cartItemDTO=new CartItemDTO();
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
        cartItemDTO.setProductName(cartItem.getProduct().getProductName());
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        return cartItemDTO;
    }

    public CartItem toCartItem(CartItemDTO cartItemDTO){
        Product product = productRepo.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", cartItemDTO.getProductId(), "Product-ID"));

        CartItem cartItem=new CartItem();
        cartItem.setCartItemId(cartItemDTO.getCartItemId());
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());

        return cartItem;
    }
}
