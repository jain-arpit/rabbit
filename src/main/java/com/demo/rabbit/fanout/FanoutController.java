package com.demo.rabbit.fanout;

import com.demo.rabbit.ApplicationConstants;
import com.demo.rabbit.Message;
import com.demo.rabbit.MessageSender;
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
@RequestMapping("/fanout-exchange")
@Log4j2
public class FanoutController {

    private final RabbitTemplate rabbitTemplate;
    private final MessageSender messageSender;

    @Autowired
    public FanoutController(RabbitTemplate rabbitTemplate, MessageSender messageSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageSender = messageSender;
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            log.info("Sending message to fanout exchange");
            messageSender.sendMessage(rabbitTemplate, message.getExchange(), "", message);
            return new ResponseEntity<>(ApplicationConstants.IN_QUEUE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception while  sending message to the  queue", e);
            return new ResponseEntity<>(ApplicationConstants.MESSAGE_QUEUE_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



