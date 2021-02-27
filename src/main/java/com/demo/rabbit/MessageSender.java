package com.demo.rabbit;


import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageSender {


    /**
     * @param rabbitTemplate rabbit template  to send message
     * @param exchange       exchange name
     * @param routingKey     routing key
     * @param data           data to publish
     */
    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
        log.info("Sending message to the  queue using  routing key: {}. Message:{}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("Message has been sent to the  queue");
    }

    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, Message message) {
        log.info("Sending message to the  queue using.  Exchange: {}", exchange);
        rabbitTemplate.send(exchange, "", message);
        log.info("Message has been sent to the  queue");
    }
}
