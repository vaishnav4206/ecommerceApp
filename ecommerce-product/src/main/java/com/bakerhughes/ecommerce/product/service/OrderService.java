package com.bakerhughes.ecommerce.product.service;

import com.bakerhughes.ecommerce.product.model.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
// remove this class as we don't need this in Product
@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String orderRoutingKey;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendOrderMessage(OrderRequest orderRequest) {
        try {
            // Convert OrderRequest to JSON
            String message = objectMapper.writeValueAsString(orderRequest);
            rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // TO DO: Handle the exception accordingly
        }
    }
    public void sendOrderMessage(String message) {
        rabbitTemplate.convertAndSend(orderExchange, orderRoutingKey, message);
    }

    public void sendOrderMessage() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId("vaishnav");
        orderRequest.setProductIds(List.of(1L, 2L, 3L));
        orderRequest.setRushDelivery(true);
        orderRequest.setTotalAmount(BigDecimal.valueOf(99.99));
        rabbitTemplate.convertAndSend("orderExchange", "order.new",orderRequest);
    }

}
