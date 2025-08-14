package com.ex.finance_service.consumer;

import com.ex.finance_service.config.RabbitConfig;
import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ex.finance_service.config.RabbitConfig.ROUTE_EVENTS_QUEUE;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;


//    @RabbitListener(queues = ROUTE_EVENTS_QUEUE)
//    public void receive(SendRouteEventsRequestDto responseDto) {
//        System.out.println("Received message from RabbitMQ(route): " + responseDto);
//    }

//    @RabbitListener(queues = ROUTE_EVENTS_QUEUE)
//    public void receive(String json) throws IOException {
//        SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(json, SendRouteEventsRequestDto.RouteEventDto.class);
//        System.out.println("Received message from RabbitMQ(route): " + dto);
//        throw new RuntimeException("Force send to DLX");
//    }

//    todo короче я вижу так: отправляется первый раз, падает,
//     отправляется в reply, там чилит 10 сек, стухает и отправляется в dlx.
//     Сам reply отправку повторную не делает, почему gpt решил, что должен дедлать хз

    @RabbitListener(queues = ROUTE_EVENTS_QUEUE)
    public void receive(Message message, Channel channel) throws IOException {
        System.out.println("Received: " + message);
        // если бросаем исключение, Rabbit сам отправит в retry или dead
//        throw new RuntimeException("Test fail");
//        String json = new String(message.getBody(), StandardCharsets.UTF_8);
//        SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(json, SendRouteEventsRequestDto.RouteEventDto.class);

    }

//    @RabbitListener(queues = "dlx.queue")
//    public void receiveFromDlx(String json) {
//        System.out.println("📥 [DLX] Received dead-letter message: " + json);
//    }
}