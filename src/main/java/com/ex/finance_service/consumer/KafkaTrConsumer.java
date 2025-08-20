package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaTrConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "tr-topic", containerFactory = "kafkaTrListenerContainerFactory")
    public void listenTrTopic(String message) throws JsonProcessingException {
            SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
            System.out.println("Kafka tr consumer received message: " + dto.getRouteEventId());

    }
}
