package com.bakerhughes.ecommerce.order.service;

import com.bakerhughes.ecommerce.order.model.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "orderQueue")
    public void handleMessage(String message) {
        try {
            OrderRequest orderRequest = objectMapper.readValue(message, OrderRequest.class);
            // Process the order
            System.out.println("Received Order request: " + orderRequest);
            System.out.println("Received Order request - user: " + orderRequest.getUserId());
        } catch (Exception e) {
            // TO DO: exception handling
            e.printStackTrace();
        }
    }
}
