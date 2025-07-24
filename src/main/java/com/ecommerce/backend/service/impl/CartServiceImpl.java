package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.CartItemRepo;
import com.ecommerce.backend.repositories.CartRepo;
import com.ecommerce.backend.dto.CartDTO;
import com.ecommerce.backend.mapping.CartItemMapping;
import com.ecommerce.backend.mapping.CartMapping;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapping cartMapping;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartItemMapping cartItemMapping;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public CartDTO saveCart(CartDTO cartDTO) {
        Cart cart=cartMapping.toCart(cartDTO);
        cart.setCreatedAt(LocalDateTime.now());

        Cart savedCart=cartRepo.save(cart);

        for(CartItem cartItem:savedCart.getCartItems()){
            cartItem.setCart(savedCart);
        }
        cartItemRepo.saveAll(savedCart.getCartItems());

        return cartMapping.toCartDTO(savedCart);
    }

    @Override
    public CartDTO deleteCart(Long cartId) {
        Cart cart=cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart",cartId,"Cart-ID"));
        cartItemRepo.deleteAll(cart.getCartItems());
        cartRepo.delete(cart);
        return cartMapping.toCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO updateCart(CartDTO cartDTO, Long cartId) {
        // Fetch the existing Cart entity
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", cartId, "Cart-ID"));
        cartDTO.setUserId(cart.getUser().getUserId());
        List<CartItem> cartItems=cartMapping.toCart(cartDTO).getCartItems();
        cart.setCartItems(cartItems);
        // Update the user ID in the DTO (if needed)
        Cart savedCart=cartRepo.save(cart);

        for(CartItem cartItem:savedCart.getCartItems()){
            cartItem.setCart(savedCart);
        }
        for(CartItem cartItem:savedCart.getCartItems()){
            if(cartItem.getCartItemId()==null)
                cartItemRepo.save(cartItem);
        }

        // Return the updated CartDTO
        return cartMapping.toCartDTO(savedCart);
    }

    @Override
    public CartDTO getCartById(Long cartId) {
        Cart cart=cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart",cartId,"Cart-ID"));
        return cartMapping.toCartDTO(cart);
    }

    @Override
    public Page<CartDTO> getCarts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));  // Pagination and Sorting
        Page<Cart> cartPage = cartRepo.findAll(pageable);  // Fetch paginated carts from the repository

        // Convert Page<Cart> to Page<CartDTO>
        return cartPage.map(cartMapping::toCartDTO);
    }

}
