package com.bakerhughes.ecommerce.product.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String orderRoutingKey;

    public void sendOrderMessage(String message) {
        rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, message);
    }

    public void sendOrderMessage() {
        rabbitTemplate.convertAndSend("orderExchange", "order.new","hey new order placed");
    }
}
