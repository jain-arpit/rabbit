package com.demo.rabbit.queues;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfig {
    @Bean
    Queue transportQueue() {
        return new Queue("transport-queue");
    }

    @Bean
    Queue itQueue() {
        return new Queue("it-queue");
    }


    @Bean
    Queue adminQueue() {
        return new Queue("admin-queue");
    }

}
