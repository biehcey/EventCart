package com.biehcey.eventcart.eventcart.cart.dto;

import com.biehcey.eventcart.eventcart.product.dto.ProductResponseDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private int quantity;
    private BigDecimal subTotal;
    private ProductResponseDto product;
}
