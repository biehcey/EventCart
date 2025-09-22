package com.biehcey.eventcart.eventcart.cart.controller;

import com.biehcey.eventcart.eventcart.cart.dto.CartDto;
import com.biehcey.eventcart.eventcart.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(){
        CartDto cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<CartDto> addProductToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity){
        CartDto updatedCart = cartService.addProductToCart(productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDto> deleteProductFromCart(@PathVariable Long productId){
        cartService.removeProductFromCart(productId);
        CartDto updatedCart = cartService.getCart();
        return ResponseEntity.ok(updatedCart);
    }
}
