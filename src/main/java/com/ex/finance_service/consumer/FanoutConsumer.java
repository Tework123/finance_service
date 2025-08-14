package com.ex.finance_service.consumer;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FanoutConsumer {

//    @RabbitListener(queues = "service-b.queue")
//    public void handleMessage(Message message, Channel channel) throws IOException {
////        SendRouteEventsRequestDto responseDto = (SendRouteEventsRequestDto) message;
//        System.out.println("Received message from RabbitMQ(fanout): " + message);
//
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//
//        channel.basicReject(deliveryTag, false);
//    }
}