package com.biehcey.eventcart.eventcart.order.mapper;

import com.biehcey.eventcart.eventcart.order.dto.OrderDto;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    OrderDto toDto(Order order);
}
