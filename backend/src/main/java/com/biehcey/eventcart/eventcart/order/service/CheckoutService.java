package com.biehcey.eventcart.eventcart.order.service;

import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.authentication.service.UserService;
import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.cart.service.CartService;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    private final OrderMessagingService orderMessagingService;

    @Transactional
    public Order checkout(){
        User user = userService.getCurrentUser();
        Cart cart = cartService.findOrCreateCart();
        Order savedOrder = orderService.createOrderFromCart(user, cart);
        orderMessagingService.sendOrderCreatedEvent(cart, savedOrder);
        return savedOrder;
    }
}
