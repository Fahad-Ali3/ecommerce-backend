package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.OrderItemDTO;
import com.ecommerce.backend.service.OrderItemService;
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
@RequestMapping("/api/order-item")
@Tag(name = "Order-Item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id){
        return new ResponseEntity<>(orderItemService.getOrderItemById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OrderItemDTO> deleteOrderItem(@PathVariable Long id){
        return new ResponseEntity<>(orderItemService.deleteOrderItem(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrderItemDTO> addOrderItem(@RequestBody OrderItemDTO orderItemDTO){
        return new ResponseEntity<>(orderItemService.createOrderItem(orderItemDTO),HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getOrderItems(
            @RequestParam(defaultValue = "0") int page,   // Default page number is 0
            @RequestParam(defaultValue = "10") int size,  // Default page size is 10
            @RequestParam(defaultValue = "orderItemId") String sortBy) {  // Default sorting by orderItemId

        // Get the paginated and sorted order items from the service
        Page<OrderItemDTO> orderItemsPage = orderItemService.getAllOrderItems(page, size, sortBy);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("content", orderItemsPage.getContent());  // List of order items on the current page
        response.put("currentPage", orderItemsPage.getNumber());  // Current page number
        response.put("totalPages", orderItemsPage.getTotalPages());  // Total number of pages
        response.put("totalItems", orderItemsPage.getTotalElements());  // Total number of order items
        response.put("start", Math.max(orderItemsPage.getNumber() * orderItemsPage.getSize() + 1, 0));  // Starting index of the current page
        response.put("end", Math.min((orderItemsPage.getNumber() + 1) * orderItemsPage.getSize(), (int) orderItemsPage.getTotalElements()));  // Ending index of the current page
        response.put("sorted", orderItemsPage.getSort().isSorted());  // Whether the results are sorted

        // Return the response with a 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
