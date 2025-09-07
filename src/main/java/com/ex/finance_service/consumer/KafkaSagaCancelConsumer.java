package com.ex.finance_service.consumer;

import com.ex.finance_service.service.CountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSagaCancelConsumer {

    private final ObjectMapper objectMapper;
    private final CountService countService;

    @KafkaListener(topics = "saga-cancel-topic",
            groupId = "finance_service_cancel_consumer",
            containerFactory = "kafkaSagaCancelListenerContainerFactory")
    public void listenSagaCancelTopic(String message) throws JsonProcessingException, InterruptedException {
        UUID routeEventId = objectMapper.readValue(message, UUID.class);

        System.out.println("financeService consumer принял сообщение из saga-cancel-topic: " + routeEventId);

        countService.cancel(routeEventId);
    }
}
