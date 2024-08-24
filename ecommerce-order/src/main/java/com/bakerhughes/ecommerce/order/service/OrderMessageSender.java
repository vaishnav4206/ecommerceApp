package com.bakerhughes.ecommerce.order.service;

import com.bakerhughes.ecommerce.order.model.Order;
import com.bakerhughes.ecommerce.order.model.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.rabbitmq.exchange.order}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String routingKey;

    public void sendOrderMessage(Order order) {
        try {
//            String message = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend(exchange, routingKey, order);
        } catch (Exception e) {
            // TO DO:
            // exception handling
            e.printStackTrace();
        }
    }
}
