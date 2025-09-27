package com.biehcey.eventcart.eventcart.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateDto {
    private Long productId;
    private int quantity;
}
