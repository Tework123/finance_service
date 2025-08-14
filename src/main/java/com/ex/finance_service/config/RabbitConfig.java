package com.ex.finance_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    public static final String ROUTE_EVENTS_QUEUE = "route_events_queue";
    public static final String ROUTE_EVENTS_EXCHANGE = "route_events_exchange";
    public static final String ROUTE_EVENTS_ROUTING_KEY = "route_events_queue.routingKey";

    public static final String RETRY_ROUTING_KEY = "retry.routing.key";
    public static final String RETRY_QUEUE = "retry.queue";
    public static final String RETRY_EXCHANGE = "retry.exchange";

    public static final String DLX_ROUTING_KEY = "dlx.routing.key";
    public static final String DLX_QUEUE = "dlx.queue";
    public static final String DLX_EXCHANGE = "dlx.exchange";

    public static final String EXCHANGE_FANOUT = "fanout";


    @Bean
    public Queue routeEventsQueue() {
        Map<String, Object> args = new HashMap<>();
        // Указываем DLX, куда попадут "мертвые" сообщения
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
//        args.put("x-message-ttl", 10000); // 10 секунд TTL
        return new Queue(ROUTE_EVENTS_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding routeEventsBinding() {
        return BindingBuilder.bind(routeEventsQueue()).to(routeEventsExchange()).with(ROUTE_EVENTS_ROUTING_KEY);
    }

    @Bean
    public TopicExchange routeEventsExchange() {
        return new TopicExchange(ROUTE_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLX_QUEUE)
                .withArgument("x-message-ttl", 600000) // 10 мин поставить
                .withArgument("x-dead-letter-exchange", ROUTE_EVENTS_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ROUTE_EVENTS_ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLX_ROUTING_KEY);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    //    @Bean
//    public Queue serviceBQueue() {
//        return new Queue("service-b.que   ue", true);
//    }
//
//    @Bean
//    public Binding bindingB(FanoutExchange deliveryExchange, Queue serviceBQueue) {
//        return BindingBuilder.bind(serviceBQueue).to(deliveryExchange);
//    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
}
