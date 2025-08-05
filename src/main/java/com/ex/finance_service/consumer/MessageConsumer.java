package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.ex.finance_service.config.RabbitConfig.ROUTE_EVENTS_QUEUE;

@Component
public class MessageConsumer {

    @RabbitListener(queues = ROUTE_EVENTS_QUEUE)
    public void receive(SendRouteEventsRequestDto responseDto) {
        System.out.println("Received message from RabbitMQ: " + responseDto);
    }
}