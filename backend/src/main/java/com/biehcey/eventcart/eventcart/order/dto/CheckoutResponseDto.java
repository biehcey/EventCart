package com.biehcey.eventcart.eventcart.order.dto;

import com.biehcey.eventcart.eventcart.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CheckoutResponseDto {
    private Long orderId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderItemDto> items;
}
