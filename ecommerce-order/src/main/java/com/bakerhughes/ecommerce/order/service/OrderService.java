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

    private static final String PENDING = "PENDING";
    private static final String PROCESSING = "PROCESSING";
    private static final String RUSH_PROCESSING = "RUSH_DELIVERY_PROCESSING";
    private static final String COMPLETED = "COMPLETED";
    private static final String ORDER_STATUS_PRE_TEXT = "Your order status is now: ";

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
        order.setStatus(PENDING);

        // Save the order to the database
        orderRepository.save(order);

        // Send the order to the queue
        orderMessageSender.sendOrderMessage(order);

        return order;
    }

    public List<Order> getOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public void processStandardOrder(Order order) {
        System.out.println("processing order: " + order.getProductIds());

        try {
            // Special handling for rush delivery
            if (order.isRushDelivery()) {
                handleRushDelivery(order);
            }
            else {
                // Update the order status to PROCESSING
                order.setStatus(PROCESSING);

                // Notify the user via WebSocket
                notificationService.sendOrderStatusUpdate(order.getUserId(), ORDER_STATUS_PRE_TEXT + order.getStatus());
            }


            // Further order processing logic (e.g., payment processing, inventory management)
            // This could involve other microservices or further message queue interactions
            // adding a delay to mock the incoming notification
            Thread.sleep(6000);
            // adding a mock notification to indicate the delivery team is at the user's location
            // for the future scope of this project, we can add a delivery service where,
            // delivery microservice can be created and push an update in to the same notification queue
            notificationService.sendOrderStatusUpdate(order.getUserId(),  "Delivery team reached your location!");
            Thread.sleep(6000);
            // Finalize the order
            finalizeOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
            // TO DO: Implement retry logic or mark order as FAILED
        }
    }

    private void handleRushDelivery(Order order) {
        // Update order status for rush delivery processing
        order.setStatus(RUSH_PROCESSING);


        // Notify the user via WebSocket
        notificationService.sendOrderStatusUpdate(order.getUserId(), ORDER_STATUS_PRE_TEXT + order.getStatus());


        // TODO: Add additional rush delivery logic here, such as prioritizing the order in the system
    }

    private void finalizeOrder(Order order) {
        System.out.println("finalizing order: " + order.getProductIds());
        // Finalize the order (e.g., mark as COMPLETED, trigger shipping, etc.)
        order.setStatus(COMPLETED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Notify the user of order completion
        notificationService.sendOrderStatusUpdate(order.getUserId(), ORDER_STATUS_PRE_TEXT + order.getStatus());

    }

}
