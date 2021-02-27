package com.demo.rabbit.direct;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DirectConfig {
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

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("rabbit-direct-exchange");
    }

    @Bean
    Binding transportBinding(Queue transportQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(transportQueue).to(directExchange).with("transport");
    }

    @Bean
    Binding adminBinding(Queue adminQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(adminQueue).to(directExchange).with("admin");
    }

    @Bean
    Binding itBinding(Queue itQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(itQueue).to(directExchange).with("it");
    }

}
