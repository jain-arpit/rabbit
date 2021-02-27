package com.demo.rabbit.fanout;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutConfig {

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    Binding fanoutTransportBinding(Queue transportQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(transportQueue).to(fanoutExchange);
    }

    @Bean
    Binding fanoutAdminBinding(Queue adminQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(adminQueue).to(fanoutExchange);
    }

    @Bean
    Binding fanoutItBinding(Queue itQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(itQueue).to(fanoutExchange);
    }

}
