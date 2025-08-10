package com.ex.finance_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    public static final String ROUTE_EVENTS_QUEUE = "route_events_queue";
    public static final String EXCHANGE_NAME = "delivery";
    public static final String ROUTE_EVENTS_QUEUE_ROUTING_KEY = "route_events_queue.routingKey";
    public static final String EXCHANGE_FANOUT = "fanout";

    public static final String RETRY_QUEUE = "retry.queue";
    public static final String ROUTING_KEY = "route.key";

    //     пытался настроить ретраи, не получилось, но должно быть так:
    //     ошибка падает, отправляет мертвое сообщение в retry очередь,
    //     там оно ждет 10 сек, потом снова идет в основную.
    //     А если еще раз упадет, то в dead.(идеально было бы)
    @Bean
    public Queue mainQueue() {
        Map<String, Object> args = new HashMap<>();
        // Указываем DLX, куда попадут "мертвые" сообщения
        args.put("x-dead-letter-exchange", EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", RETRY_QUEUE);
        args.put("x-message-ttl", 10000); // 10 секунд TTL


        return new Queue(ROUTE_EVENTS_QUEUE, true, false, false, args);
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

    //    dead
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dlx.exchange");
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("dlx.queue");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("dlx.routing.key");
    }

    // Очередь retry с TTL и dead-letter обратно в основную очередь
    @Bean
    public Queue retryQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000); // 10 секунд ожидания перед повторной попыткой
        args.put("x-dead-letter-exchange", EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", ROUTE_EVENTS_QUEUE_ROUTING_KEY);
        return new Queue(RETRY_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding retryBinding() {
        return BindingBuilder.bind(retryQueue()).to(deadLetterExchange()).with(RETRY_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
}
