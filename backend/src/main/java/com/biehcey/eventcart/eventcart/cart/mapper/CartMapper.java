package com.biehcey.eventcart.eventcart.cart.mapper;

import com.biehcey.eventcart.eventcart.cart.dto.CartDto;
import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toEntity(CartDto dto);
    CartDto toDto(Cart cart);
}
