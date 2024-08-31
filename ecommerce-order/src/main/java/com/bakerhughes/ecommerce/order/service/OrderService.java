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
    @Autowired
    private NotificationService notificationService;

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

    public void processStandardOrder(Order order) {
        try {
            // Special handling for rush delivery
            if (order.isRushDelivery()) {
                handleRushDelivery(order);
            }

            // Update the order status to PROCESSING
            order.setStatus("PROCESSING");

            // Send notification to the user
            notificationService.sendOrderConfirmation(order.getUserId(), order);

            // Further order processing logic (e.g., payment processing, inventory management)
            // This could involve other microservices or further message queue interactions

            // Finalize the order
            finalizeOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
            // TO DO: Implement retry logic or mark order as FAILED
        }
    }

    private void handleRushDelivery(Order order) {
        // Update order status for rush delivery processing
        order.setStatus("RUSH_DELIVERY_PROCESSING");

        // Send notification to the user about rush delivery
        notificationService.sendRushDeliveryNotification(order);

        // TO DO: Add additional rush delivery logic here, such as prioritizing the order in the system
    }

    private void finalizeOrder(Order order) {
        // Finalize the order (e.g., mark as COMPLETED, trigger shipping, etc.)
        order.setStatus("COMPLETED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Notify the user of order completion
        notificationService.sendOrderCompletionNotification(order.getUserId(), order);
    }

}
