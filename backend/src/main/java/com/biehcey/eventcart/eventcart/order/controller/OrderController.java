package com.biehcey.eventcart.eventcart.order.controller;

import com.biehcey.eventcart.eventcart.order.dto.OrderDto;
import com.biehcey.eventcart.eventcart.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getMyOrders(){
        List<OrderDto> orders = orderService.findByCustomer();
        return ResponseEntity.ok(orders);
    }
}
