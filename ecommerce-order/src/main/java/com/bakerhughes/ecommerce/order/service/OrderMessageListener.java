package com.bakerhughes.ecommerce.order.service;

import com.bakerhughes.ecommerce.order.model.Order;
import com.bakerhughes.ecommerce.order.model.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    OrderService orderService;

    @RabbitListener(queues = "orderQueue")
    public void handleMessage(String message) {
        try {

            Order orderRequest = objectMapper.readValue(message, Order.class);
            // Process the order
            System.out.println("Received Order request: " + orderRequest);
            // Process the order
            orderService.processStandardOrder(orderRequest);

        } catch (Exception e) {
            // TO DO: exception handling
            e.printStackTrace();
        }
    }


}
