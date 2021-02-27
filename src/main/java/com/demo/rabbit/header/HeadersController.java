package com.demo.rabbit.header;

import com.demo.rabbit.ApplicationConstants;
import com.demo.rabbit.Message;
import com.demo.rabbit.MessageSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/headers-exchange")
@Log4j2
public class HeadersController {

    private final RabbitTemplate rabbitTemplate;
    private final MessageSender messageSender;

    @Autowired
    public HeadersController(RabbitTemplate rabbitTemplate, MessageSender messageSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageSender = messageSender;
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            log.info("Sending message to headers exchange");
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("department", message.getDepartment());
            MessageConverter messageConverter = new Jackson2JsonMessageConverter();
            org.springframework.amqp.core.Message convertedMessage = messageConverter.toMessage(message, messageProperties);
            messageSender.sendMessage(rabbitTemplate, message.getExchange(), convertedMessage);
            return new ResponseEntity<>(ApplicationConstants.IN_QUEUE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception while  sending message to the  queue", e);
            return new ResponseEntity<>(ApplicationConstants.MESSAGE_QUEUE_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



