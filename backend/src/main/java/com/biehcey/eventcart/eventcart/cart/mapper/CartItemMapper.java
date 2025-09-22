package com.biehcey.eventcart.eventcart.cart.mapper;

import com.biehcey.eventcart.eventcart.cart.dto.CartItemDto;
import com.biehcey.eventcart.eventcart.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "product.owner", ignore = true)
    CartItem toEntity(CartItemDto dto);

    @Mapping(target = "product.owner", ignore = true)
    CartItemDto toDto(CartItem cartItem);
}
