package com.biehcey.eventcart.eventcart.cart.mapper;

import com.biehcey.eventcart.eventcart.cart.dto.CartItemDto;
import com.biehcey.eventcart.eventcart.cart.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemDto dto);
    CartItemDto toDto(CartItem cartItem);
}
