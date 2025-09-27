package com.biehcey.eventcart.eventcart.order.service;

import com.biehcey.eventcart.eventcart.cart.entity.Cart;
import com.biehcey.eventcart.eventcart.order.entity.Order;
import com.biehcey.eventcart.eventcart.util.OrderCreatedDto;
import com.biehcey.eventcart.eventcart.util.StockUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMessagingService {
    private final KafkaTemplate<String, OrderCreatedDto> kafkaTemplate;

    public void sendOrderCreatedEvent(Cart cart, Order order){

        OrderCreatedDto orderCreatedEvent = new OrderCreatedDto(
                cart.getId(),order.getId(), order.getItems().stream()
                .map(item -> new StockUpdateDto(item.getProductId(), item.getQuantity())).toList()
        );
        kafkaTemplate.send("order-created-topic-v2", orderCreatedEvent);
    }

}
