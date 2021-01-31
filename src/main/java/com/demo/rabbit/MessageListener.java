package com.demo.rabbit;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Log4j2
public class MessageListener {

    @RabbitListener(queues = "${app.queue.name}")
    public void receiveMessage(final UserDetails data) {
        log.info("Message received from queue. Data:{}", data);
        try {
            log.info("Making call to external api to send message");

        } catch (HttpClientErrorException e) {

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Throwing exception so that message can be re-queued");
                log.info("Adding delay of 2 second");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    log.error("Error in sleep", interruptedException);
                }
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(e);
            }
        } catch (Exception e) {
            log.error("Internal server error occurred. Bypassing message requeue", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
