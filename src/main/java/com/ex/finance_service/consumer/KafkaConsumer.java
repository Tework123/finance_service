package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "my-topic", groupId = "my-json-group")
    public void listen(String message) throws JsonProcessingException {
            SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
            System.out.println("Kafka consumer received message: " + dto.getRouteEventId());

    }

    @KafkaListener(topics = "request-topic", groupId = "my-json-group")
    @SendTo  // <- Spring использует reply-topic из заголовка
    public String listenSync(String message){
        try {
            SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
            System.out.println("Kafka consumer received message: " + dto.getRouteEventId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "yes, i am accept you message";
    }
}