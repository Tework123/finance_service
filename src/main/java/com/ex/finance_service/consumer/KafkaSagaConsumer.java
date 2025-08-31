package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.finance_service.producer.KafkaSagaProducer;
import com.ex.finance_service.service.CountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSagaConsumer {

    private final ObjectMapper objectMapper;
    private final KafkaSagaProducer kafkaSagaProducer;
    private final CountService countService;

    @KafkaListener(topics = "saga-main-topic", containerFactory = "kafkaSagaMainListenerContainerFactory")
    public void listenSagaMainTopic(String message) throws JsonProcessingException, InterruptedException {
        SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
        System.out.println("financeService consumer принял сообщение из saga-main-topic: " + dto.getRouteEventId());

        countService.save(dto);

        kafkaSagaProducer.sendMessage("saga-success-topic", dto.getRouteEventId());
        // пишем в базу
//        отправляем в другой топик сообщение
//        если exception то в другой топик, а если при отправке туда тоже exception? gg
    }
}
