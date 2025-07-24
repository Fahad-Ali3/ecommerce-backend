package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.CartItemDTO;
import com.ecommerce.backend.service.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart-item")
@Tag(name = "Cart-Item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Map<String, Object>> getCartItems(
            @RequestParam(defaultValue = "0") int page,        // Default page is 0
            @RequestParam(defaultValue = "10") int size,       // Default size is 10
            @RequestParam(defaultValue = "cartItemId") String sortBy) {  // Default sorting by cartItemId

        // Get the paginated and sorted cart items from the service
        Page<CartItemDTO> cartItemPage = cartItemService.getAllCarts(page, size, sortBy);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("content", cartItemPage.getContent());  // List of cart items on the current page
        response.put("currentPage", cartItemPage.getNumber());  // Current page number
        response.put("totalPages", cartItemPage.getTotalPages());  // Total number of pages
        response.put("totalItems", cartItemPage.getTotalElements());  // Total number of cart items
        response.put("start", Math.max(cartItemPage.getNumber() * cartItemPage.getSize() + 1, 0));  // Starting index of the current page
        response.put("end", Math.min((cartItemPage.getNumber() + 1) * cartItemPage.getSize(), (int) cartItemPage.getTotalElements()));  // Ending index of the current page
        response.put("sorted", cartItemPage.getSort().isSorted());  // Whether the results are sorted

        // Return the response with a 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id){
        return new ResponseEntity<>(cartItemService.getCartItemById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CartItemDTO> deleteCartItem(@PathVariable Long id){
        return new ResponseEntity<>(cartItemService.deleteCartItem(id),HttpStatus.OK);
    }
}
