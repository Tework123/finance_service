package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    @RabbitListener(queues = "service-b.queue")
    public void handleMessage(SendRouteEventsRequestDto responseDto) {
        System.out.println("Service B received: " + responseDto);
    }
}
