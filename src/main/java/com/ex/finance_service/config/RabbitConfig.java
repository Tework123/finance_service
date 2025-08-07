package com.ex.finance_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ROUTE_EVENTS_QUEUE = "route_events_queue";
    public static final String EXCHANGE_NAME = "delivery";
    public static final String ROUTE_EVENTS_QUEUE_ROUTING_KEY = "route_events_queue.routingKey";
    public static final String EXCHANGE_FANOUT = "fanout";

    @Bean
    public Queue mainQueue() {
        return new Queue(ROUTE_EVENTS_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding mainBinding(Queue mainQueue, TopicExchange exchange) {
        return BindingBuilder.bind(mainQueue).to(exchange).with(ROUTE_EVENTS_QUEUE_ROUTING_KEY);
    }

    @Bean
    public FanoutExchange deliveryExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    public Queue serviceBQueue() {
        return new Queue("service-b.queue", true);
    }

    @Bean
    public Binding bindingB(FanoutExchange deliveryExchange, Queue serviceBQueue) {
        return BindingBuilder.bind(serviceBQueue).to(deliveryExchange);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
