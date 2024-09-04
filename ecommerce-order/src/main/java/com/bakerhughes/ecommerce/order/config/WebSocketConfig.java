package com.bakerhughes.ecommerce.order.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * configureMessageBroker: /topic is used for broadcasting updates to subscribed users.
     * registerStompEndpoints: /ws-notifications is the WebSocket endpoint for the client to connect.
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory message broker for order status updates
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define a WebSocket endpoint that clients will connect to
        registry.addEndpoint("/ws-notifications")
                .setAllowedOrigins("http://localhost:4200").withSockJS();
    }
}
