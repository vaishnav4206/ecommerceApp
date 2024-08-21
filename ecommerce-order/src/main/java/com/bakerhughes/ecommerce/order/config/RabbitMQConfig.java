package com.bakerhughes.ecommerce.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue", true);
    }

    @Bean
    public Queue statusQueue() {
        return new Queue("statusQueue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("orderExchange");
    }

    @Bean
    public Binding bindingOrderQueue(Queue orderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange).with("order.#");
    }

    @Bean
    public Binding bindingStatusQueue(Queue statusQueue, TopicExchange exchange) {
        return BindingBuilder.bind(statusQueue).to(exchange).with("status.#");
    }

    // Optionally: Configure RabbitTemplate for sending messages
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
