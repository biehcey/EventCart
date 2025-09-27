package com.biehcey.eventcart.eventcart.order.controller;

import com.biehcey.eventcart.eventcart.cart.repository.CartRepository;
import com.biehcey.eventcart.eventcart.order.dto.CheckoutResponseDto;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import com.biehcey.eventcart.eventcart.order.mapper.CheckoutMapper;
import com.biehcey.eventcart.eventcart.order.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final CheckoutMapper checkoutMapper;

    @PostMapping("/{cartId}")
    public ResponseEntity<CheckoutResponseDto> checkout(){

        Order order = checkoutService.checkout();

        CheckoutResponseDto responseDto = checkoutMapper.toCheckoutResponse(order);
        return ResponseEntity.ok(responseDto);
    }

}
