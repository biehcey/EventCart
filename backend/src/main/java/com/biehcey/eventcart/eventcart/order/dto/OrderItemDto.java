package com.biehcey.eventcart.eventcart.order.dto;

import com.biehcey.eventcart.eventcart.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
