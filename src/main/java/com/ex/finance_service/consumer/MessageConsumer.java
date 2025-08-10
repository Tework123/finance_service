package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ex.finance_service.config.RabbitConfig.ROUTE_EVENTS_QUEUE;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final ObjectMapper objectMapper;


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

    @RabbitListener(queues = ROUTE_EVENTS_QUEUE)
    public void receive(Message message, Channel channel) throws IOException {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(json, SendRouteEventsRequestDto.RouteEventDto.class);
        System.out.println("Received message from RabbitMQ(route): " + dto);

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // Здесь обработка
            throw new RuntimeException("Force send to DLX");
        } catch (Exception e) {
            // Отклоняем сообщение, не перекидываем в очередь
            channel.basicReject(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "retry.queue")
    public void receiveRetry(String msg) {
        System.out.println("Retry queue got: " + msg);
    }

//    @RabbitListener(queues = "dlx.queue")
//    public void receiveFromDlx(String json) {
//        System.out.println("📥 [DLX] Received dead-letter message: " + json);
//    }
}