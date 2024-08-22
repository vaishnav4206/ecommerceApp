package com.bakerhughes.ecommerce.order.service;

import com.bakerhughes.ecommerce.order.model.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${spring.rabbitmq.exchange.order}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String routingKey;

    public void sendOrderMessage(OrderRequest orderRequest) {
        try {
            String message = new ObjectMapper().writeValueAsString(orderRequest);
            amqpTemplate.convertAndSend(exchange, routingKey, message);
        } catch (Exception e) {
            // TO DO:
            // exception handling
            e.printStackTrace();
        }
    }
}
