package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.CartDTO;
import com.ecommerce.backend.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@Tag(name = "Cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("user/{userId}/cart/")
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CartDTO cartDTO, @PathVariable Long userId){
        cartDTO.setUserId(userId);

        return new ResponseEntity<>(cartService.saveCart(cartDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("cart/{cartId}/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CartDTO> deleteCart(@PathVariable Long cartId){
        return new ResponseEntity<>(cartService.deleteCart(cartId),HttpStatus.OK);
    }

    @PutMapping("cart/{cartId}/")
    public ResponseEntity<CartDTO> updateCart(@Valid @RequestBody CartDTO cartDTO,@PathVariable Long cartId){
        return new ResponseEntity<>(cartService.updateCart(cartDTO,cartId),HttpStatus.CREATED);
    }

    @GetMapping("cart/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId){
        return new ResponseEntity<>(cartService.getCartById(cartId),HttpStatus.OK);
    }

    @GetMapping("carts")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Map<String, Object>> getCarts(
            @RequestParam(defaultValue = "0") int page,        // Default page is 0
            @RequestParam(defaultValue = "10") int size,       // Default size is 10
            @RequestParam(defaultValue = "cartId") String sortBy) {  // Default sorting by cartId

        // Get the paginated and sorted carts from the service
        Page<CartDTO> cartPage = cartService.getCarts(page, size, sortBy);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("content", cartPage.getContent());  // List of carts on the current page
        response.put("currentPage", cartPage.getNumber());  // Current page number
        response.put("totalPages", cartPage.getTotalPages());  // Total number of pages
        response.put("totalItems", cartPage.getTotalElements());  // Total number of carts
        response.put("start", Math.max(cartPage.getNumber() * cartPage.getSize() + 1, 0));  // Starting index of the current page
        response.put("end", Math.min((cartPage.getNumber() + 1) * cartPage.getSize(), (int) cartPage.getTotalElements()));  // Ending index of the current page
        response.put("sorted", cartPage.getSort().isSorted());  // Whether the results are sorted

        // Return the response with a 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


