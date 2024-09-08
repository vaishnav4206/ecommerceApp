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
        orderMessageSender.sendNotification(statusUpdate);
    }

    @RabbitListener(queues = "notification-queue")
    public void listenForNotifications(String message) {
        try {
            // whenever notification queue receives a message
            // it will send the message to web socket to process notification to UI
            messagingTemplate.convertAndSend("/topic/order-status-updates", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}