package com.demo.rabbit;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfigReader applicationConfigReader;
    private final MessageSender messageSender;

    @Autowired
    public UserController(RabbitTemplate rabbitTemplate, ApplicationConfigReader applicationConfigReader, MessageSender messageSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationConfigReader = applicationConfigReader;
        this.messageSender = messageSender;
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody UserDetails userDetails) {
        try {
            messageSender.sendMessage(rabbitTemplate, applicationConfigReader.getExchange(), applicationConfigReader.getRoutingKey(), userDetails);
            return new ResponseEntity<>(ApplicationConstants.IN_QUEUE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception while  sending message to the  queue", e);
            return new ResponseEntity<>(ApplicationConstants.MESSAGE_QUEUE_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



