package com.biehcey.eventcart.eventcart.order.mapper;

import com.biehcey.eventcart.eventcart.order.dto.CheckoutResponseDto;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    @Mapping(source = "id", target = "orderId")
    CheckoutResponseDto toCheckoutResponse(Order order);

}
