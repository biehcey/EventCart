package com.biehcey.eventcart.eventcart.cart.dto;

import com.biehcey.eventcart.eventcart.cart.entity.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private BigDecimal totalPrice;
    private List<CartItemDto> items;
}
