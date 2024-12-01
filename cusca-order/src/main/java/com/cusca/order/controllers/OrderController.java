package com.cusca.order.controllers;


import com.cusca.order.dto.Response.OrderResponseDto;
import com.cusca.order.models.Order;
import com.cusca.order.services.OrderService;
import com.cusca.order.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public Order getOrder() {
        return new Order(1L, new ArrayList<>());
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<OrderResponseDto> addProductToOrder(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        Long userId = jwtUtil.extractUserId(token);
        return ResponseEntity.ok(orderService.addProductToOrder(userId, productId, token));
    }

    @GetMapping
    public Order getOrder(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserId(token);
        return orderService.getOrder(userId);
    }
}