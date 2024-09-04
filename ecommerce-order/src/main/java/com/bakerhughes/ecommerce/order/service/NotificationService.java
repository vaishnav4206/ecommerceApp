package com.bakerhughes.ecommerce.order.service;


import com.bakerhughes.ecommerce.order.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private OrderMessageSender orderMessageSender;

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOrderStatusUpdate(String userId, String statusUpdate) {
        // Send the update to a specific user via WebSocket
//        messagingTemplate.convertAndSend("/topic/order-status/" + userId, statusUpdate);
//        messagingTemplate.convertAndSend("/topic/order-status/" + "hello");
        orderMessageSender.sendNotification(statusUpdate);
    }

    public void sendOrderConfirmation(String userId, Order order) {
        // Implement logic to send order confirmation notification
        System.out.println("Sending order confirmation to user ID: " + userId);
        // Example: Send email, SMS, push notification, etc.
    }

    public void sendOrderCompletionNotification(String userId, Order order) {
        // Implement logic to send order completion notification
        System.out.println("Sending order completion notification to user ID: " + userId);
        messagingTemplate.convertAndSend("/topic/order-status-updates", "complete");
    }

    public void sendRushDeliveryNotification(Order order) {
        // TODO: Implement the actual notification logic, such as sending an email or in-app notification
        System.out.println("Rush delivery notification sent for order ID: " + order.getId());
    }

    @RabbitListener(queues = "notification-queue")
    public void listenForNotifications(String message) {
        try {
            // Here you can customize the destination where the message will be sent
            // For example, sending the message to a specific topic that UI is subscribed to
            messagingTemplate.convertAndSend("/topic/order-status-updates", message);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }
}