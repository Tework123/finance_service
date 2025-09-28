package com.ex.finance_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaSagaCancelProducer {

    private final KafkaTemplate<String, String> kafkaSagaCancelTemplate;
    private final ObjectMapper objectMapper;

    public KafkaSagaCancelProducer(@Qualifier("kafkaSagaCancelTemplate") KafkaTemplate<String, String> kafkaSagaCancelTemplate, ObjectMapper objectMapper) {
        this.kafkaSagaCancelTemplate = kafkaSagaCancelTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToCancelTopic(UUID id) throws JsonProcessingException, InterruptedException {
        String json = objectMapper.writeValueAsString(id);

        kafkaSagaCancelTemplate.send("saga-cancel-topic", json);

        System.out.println("Finance_service producer отправил подтверждение" +
                " ошибочной транзакции в saga-cancel-topic: " + id);
    }
}
