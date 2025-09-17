package com.biehcey.eventcart.eventcart.product.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotificationService {
    Logger logger = Logger.getLogger(NotificationService.class.getName());

    @KafkaListener(topics = {"low-stock-topic"}, groupId = "eventcart-consumer-group")
    public void listenLowStockTopic(String message){
        logger.info("Received>>>" + message);
    }

    @KafkaListener(topics = {"new-user-topic"}, groupId = "user-consumer-group")
    public void listenNewUserTopic(String message){

        logger.info("New user " + message);
    }
}
