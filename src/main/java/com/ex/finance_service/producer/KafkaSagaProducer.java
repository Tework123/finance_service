package com.ex.finance_service.producer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaSagaProducer {

    private final KafkaTemplate<String, String> kafkaSagaSuccessTemplate;
    private final ObjectMapper objectMapper;

    public KafkaSagaProducer(@Qualifier("kafkaSagaSuccessTemplate") KafkaTemplate<String, String> kafkaSagaMainTemplate, ObjectMapper objectMapper) {
        this.kafkaSagaSuccessTemplate = kafkaSagaMainTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String topic, UUID routeEventId) throws JsonProcessingException, InterruptedException {
        String json = objectMapper.writeValueAsString(routeEventId);

        kafkaSagaSuccessTemplate.send(topic, json);

        System.out.println("Finance_service producer отправил подтверждение" +
                " успешной транзакции в saga-success-topic: " + routeEventId);
    }
}
