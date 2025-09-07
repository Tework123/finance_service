package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.finance_service.producer.KafkaSagaCancelProducer;
import com.ex.finance_service.producer.KafkaSagaSuccessProducer;
import com.ex.finance_service.service.CountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSagaMainConsumer {

    private final ObjectMapper objectMapper;
    private final KafkaSagaSuccessProducer kafkaSagaSuccessProducer;
    private final KafkaSagaCancelProducer kafkaSagaCancelProducer;
    private final CountService countService;

//    @Transactional
    @KafkaListener(topics = "saga-main-topic",
            groupId = "finance_service_main_consumer",
            containerFactory = "kafkaSagaMainListenerContainerFactory")
    public void listenSagaMainTopic(String message) throws JsonProcessingException, InterruptedException {
//        а если падает сериализация?
        SendRouteEventsRequestDto.RouteEventDto dto = objectMapper.readValue(message, SendRouteEventsRequestDto.RouteEventDto.class);
        try {
            System.out.println("financeService consumer принял сообщение из saga-main-topic: " + dto.getRouteEventId());

            countService.save(dto);
            throw new Exception();
// е если упадет при отправке в success топик??
//            kafkaSagaSuccessProducer.sendMessageToSuccessTopic(dto.getRouteEventId());
        } catch (Exception e) {
            System.err.println("Ошибка обработки: " + e.getMessage());
            try {
//               todo  в базе здесь также new лежит, нихуя не понимаю что происходит.
                countService.cancel(dto.getRouteEventId());
                kafkaSagaCancelProducer.sendMessageToCancelTopic(dto.getRouteEventId());
            } catch (Exception fatal) {
                // здесь можно писать в базу или файл, как "последний шанс"
                log.error("Не удалось отправить даже в cancel-topic.", fatal);
            }

        }

    }
}
