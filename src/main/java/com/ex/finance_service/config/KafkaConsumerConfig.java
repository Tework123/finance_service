package com.ex.finance_service.config;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, SendRouteEventsRequestDto.RouteEventDto> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, SendRouteEventsRequestDto.RouteEventDto> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-json-group");
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
//                props,
//                new StringDeserializer(),
//                new JsonDeserializer<>(SendRouteEventsRequestDto.RouteEventDto.class)));
//
//        return factory;
//    }
}