package com.biehcey.eventcart.eventcart.order.service;

import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.authentication.service.UserService;
import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.order.dto.OrderDto;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import com.biehcey.eventcart.eventcart.order.entity.OrderItem;
import com.biehcey.eventcart.eventcart.order.entity.OrderStatus;
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

    public Order createOrderFromCart(User user, Cart cart){
        Order order = new Order();
        order.setCustomer(user);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.calculateSubTotal();
            return orderItem;
        }).toList();
        order.setItems(orderItems);
        order.calculateTotalPrice();

        return orderRepository.save(order);
    }
}
