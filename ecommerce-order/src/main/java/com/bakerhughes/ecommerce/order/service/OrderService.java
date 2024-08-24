package com.bakerhughes.ecommerce.order.service;

import com.bakerhughes.ecommerce.order.model.Order;
import com.bakerhughes.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMessageSender orderMessageSender;

    @Transactional
    public Order createOrder(String userId, List<Long> productIds, BigDecimal totalAmount) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductIds(productIds);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Check for rush delivery eligibility
        order.setRushDelivery(totalAmount.compareTo(BigDecimal.valueOf(100)) > 0);

        // Set initial order status
        order.setStatus("PENDING");

        // Save the order to the database
        orderRepository.save(order);

        // Send the order to the queue
        orderMessageSender.sendOrderMessage(order);

        return order;
    }

}
