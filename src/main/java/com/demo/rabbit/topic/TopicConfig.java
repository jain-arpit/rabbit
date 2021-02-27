package com.demo.rabbit.topic;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TopicConfig {

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    Binding topicTransportBinding(Queue transportQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(transportQueue).to(topicExchange).with("transport.*");
    }

    @Bean
    Binding topicAdminBinding(Queue adminQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(adminQueue).to(topicExchange).with("admin.*");
    }

    @Bean
    Binding topicItBinding(Queue itQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itQueue).to(topicExchange).with("it.*");
    }

}
