package com.biehcey.eventcart.eventcart.order.service;

import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.authentication.service.UserService;
import com.biehcey.eventcart.eventcart.order.dto.OrderDto;
import com.biehcey.eventcart.eventcart.order.mapper.OrderMapper;
import com.biehcey.eventcart.eventcart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;

    public List<OrderDto> findByCustomer(){
        User user = userService.getCurrentUser();
        return orderRepository.findByCustomer(user)
                .stream().map(orderMapper::toDto).toList();
    }
}
