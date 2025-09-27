package com.biehcey.eventcart.eventcart.order.service;

import com.biehcey.eventcart.eventcart.authentication.entity.SecureUser;
import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.order.dto.OrderDto;
import com.biehcey.eventcart.eventcart.order.mapper.OrderMapper;
import com.biehcey.eventcart.eventcart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> findByCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecureUser secureUser = (SecureUser)authentication.getPrincipal();
        User user = secureUser.getUser();
        return orderRepository.findByCustomer(user)
                .stream().map(orderMapper::toDto).toList();
    }
}
