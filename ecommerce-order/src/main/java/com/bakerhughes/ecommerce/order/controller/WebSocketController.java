package com.bakerhughes.ecommerce.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Test controller to manually send notifications to Web Socket
 */
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/order-status")
    @SendTo("/topic/order-status-updates")
    public String orderStatus(String message) throws Exception {
        return message;
    }

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestBody String message) {
        messagingTemplate.convertAndSend("/topic/order-status-updates", message);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
