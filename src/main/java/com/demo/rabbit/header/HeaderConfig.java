package com.demo.rabbit.header;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HeaderConfig {

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange("headers-exchange");
    }

    @Bean
    Binding headersTransportBinding(Queue transportQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(transportQueue).to(headersExchange).where("department").matches("transport");
    }

    @Bean
    Binding headersAdminBinding(Queue adminQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(adminQueue).to(headersExchange).where("department").matches("admin");
    }

    @Bean
    Binding headersItBinding(Queue itQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(itQueue).to(headersExchange).where("department").matches("it");
    }

    @Bean
    Binding headersExchangeBinding(Queue itQueue, HeadersExchange headersExchange, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange).to(headersExchange).where("department").matches("all");
    }


}
