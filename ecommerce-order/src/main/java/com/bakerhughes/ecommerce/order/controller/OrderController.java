package com.bakerhughes.ecommerce.order.controller;

import com.bakerhughes.ecommerce.order.model.Order;
import com.bakerhughes.ecommerce.order.model.OrderRequest;
import com.bakerhughes.ecommerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    // TO DO:
    // assign roles
    // exception handling
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {


        Order order = orderService.createOrder(
                orderRequest.getUserId(),
                orderRequest.getProductIds(),
                orderRequest.getTotalAmount()
        );
        return ResponseEntity.ok(order);
    }
}
