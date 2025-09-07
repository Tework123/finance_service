package com.ex.finance_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaSagaSuccessProducer {

    private final KafkaTemplate<String, String> kafkaSagaSuccessTemplate;
    private final ObjectMapper objectMapper;

    public KafkaSagaSuccessProducer(@Qualifier("kafkaSagaSuccessTemplate") KafkaTemplate<String, String> kafkaSagaSuccessTemplate, ObjectMapper objectMapper) {
        this.kafkaSagaSuccessTemplate = kafkaSagaSuccessTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToSuccessTopic(UUID routeEventId) throws JsonProcessingException, InterruptedException {
        String json = objectMapper.writeValueAsString(routeEventId);

        kafkaSagaSuccessTemplate.send("saga-success-topic", json);

        System.out.println("Finance_service producer отправил подтверждение" +
                " успешной транзакции в saga-success-topic: " + routeEventId);
    }
}
