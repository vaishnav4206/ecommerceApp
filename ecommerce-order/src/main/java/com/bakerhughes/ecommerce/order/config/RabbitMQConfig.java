package com.bakerhughes.ecommerce.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Main order queue
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable("orderQueue")
                .withArgument("x-dead-letter-exchange", "orderExchange")
                .withArgument("x-dead-letter-routing-key", "order.dlq")
                .build();
    }

    // Dead Letter Queue for failed order messages
    @Bean
    public Queue orderDeadLetterQueue() {
        return new Queue("order.dlq", true);
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
    public TopicExchange notificationExchange() {
        return new TopicExchange("notification-exchange");
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("notification-queue");
    }

    // Binding for the main order queue
    @Bean
    public Binding bindingOrderQueue(Queue orderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange).with("order.#");
    }

    // Binding for the dead letter queue
    @Bean
    public Binding bindingOrderDLQ(Queue orderDeadLetterQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderDeadLetterQueue).to(exchange).with("order.dlq");
    }

    // Binding for notification queue
    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with("order.status.#");
    }

    @Bean
    public Binding bindingStatusQueue(Queue statusQueue, TopicExchange exchange) {
        return BindingBuilder.bind(statusQueue).to(exchange).with("status.#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setDefaultRequeueRejected(false); // Do not requeue messages on failure
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setAdviceChain(new RetryOperationsInterceptor[] {
                RetryInterceptorBuilder.stateless()
                        .retryPolicy(new SimpleRetryPolicy(3)) // Retry 3 times
                        .build()
        });
        return factory;
    }
}