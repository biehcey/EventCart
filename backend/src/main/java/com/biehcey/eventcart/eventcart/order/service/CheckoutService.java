package com.biehcey.eventcart.eventcart.order.service;

import com.biehcey.eventcart.eventcart.authentication.entity.User;
import com.biehcey.eventcart.eventcart.authentication.service.UserService;
import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.cart.repository.CartRepository;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import com.biehcey.eventcart.eventcart.order.entity.OrderItem;
import com.biehcey.eventcart.eventcart.order.entity.OrderStatus;
import com.biehcey.eventcart.eventcart.order.repository.OrderRepository;
import com.biehcey.eventcart.eventcart.util.OrderCreatedDto;
import com.biehcey.eventcart.eventcart.util.StockUpdateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderCreatedDto> kafkaTemplate;
    private final CartRepository cartRepository;
    private final UserService userService;

    @Transactional
    public Order checkout(){
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("cart not found by user with user id" + user.getId()));
        Order order = new Order();
        order.setCustomer(user);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.calculateSubTotal();
            return orderItem;
        }).toList();
        order.setItems(orderItems);
        order.calculateTotalPrice();
        Order savedOrder = orderRepository.save(order);
        OrderCreatedDto orderCreatedEvent = new OrderCreatedDto(
                cart.getId(), order.getId(), orderItems.stream()
                .map(item -> new StockUpdateDto(item.getProductId(), item.getQuantity())).toList()
        );
        kafkaTemplate.send("order-created-topic-v2", orderCreatedEvent);
        return savedOrder;
    }
}
