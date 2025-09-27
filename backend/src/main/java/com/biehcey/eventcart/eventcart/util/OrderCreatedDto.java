package com.biehcey.eventcart.eventcart.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedDto {
    private Long cartId;
    private Long orderId;
    private List<StockUpdateDto> stockUpdateDtos;
}
