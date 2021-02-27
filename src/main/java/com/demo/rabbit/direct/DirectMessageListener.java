package com.demo.rabbit.direct;

import com.demo.rabbit.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Log4j2
public class DirectMessageListener {

    @RabbitListener(queues = "transport-queue")
    public void transportQueueConsumer(final Message data) {
        consumeMessage(data, "transport-queue");
    }

    @RabbitListener(queues = "it-queue")
    public void itQueueConsumer(final Message data) {
        consumeMessage(data, "it-queue");
    }

    @RabbitListener(queues = "admin-queue")
    public void adminQueueConsumer(final Message data) {
        consumeMessage(data, "admin-queue");
    }


    private void consumeMessage(final Message data, String queueName) {
        log.info("Message received. Queue:{} , Data:{}", queueName, data);
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


