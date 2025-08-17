package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    public KafkaConsumer(ObjectMapper jacksonObjectMapper) {
        this.objectMapper = jacksonObjectMapper;
    }

    @KafkaListener(topics = "my-topic", groupId = "my-json-group")
    public void listen(String message){
        try {
            SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
            System.out.println("Kafka consumer received message: " + dto.getRouteEventId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}