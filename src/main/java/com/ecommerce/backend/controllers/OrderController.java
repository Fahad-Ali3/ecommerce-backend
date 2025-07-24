package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.OrderDTO;
import com.ecommerce.backend.service.OrderService;
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
@Tag(name = "Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("user/{id}/orders/")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO,@PathVariable Long id) {
        orderDTO.setUserId(id);
        OrderDTO createdOrder = orderService.saveOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping("orders/")
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestParam(defaultValue = "0") int page,  // Default page number is 0 (first page)
            @RequestParam(defaultValue = "10") int size,  // Default page size is 10
            @RequestParam(defaultValue = "orderId") String sortBy) {  // Default sorting by orderId

        // Get the paginated and sorted orders from the service
        Page<OrderDTO> ordersPage = orderService.getAllOrders(page, size, sortBy);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("content", ordersPage.getContent());  // List of orders on the current page
        response.put("currentPage", ordersPage.getNumber());  // Current page number
        response.put("totalPages", ordersPage.getTotalPages());  // Total number of pages
        response.put("totalItems", ordersPage.getTotalElements());  // Total number of orders
        response.put("start", Math.max(ordersPage.getNumber() * ordersPage.getSize() + 1, 0));  // Starting index of the current page
        response.put("end", Math.min((ordersPage.getNumber() + 1) * ordersPage.getSize(), (int) ordersPage.getTotalElements()));  // Ending index of the current page
        response.put("sorted", ordersPage.getSort().isSorted());  // Whether the results are sorted

        // Return the response with a 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("orders/{orderId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long orderId,@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, orderDTO.getStatus());
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("orders/{orderId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Long orderId) {
        OrderDTO dto=orderService.deleteOrder(orderId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}