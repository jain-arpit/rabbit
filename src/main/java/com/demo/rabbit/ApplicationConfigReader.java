package com.demo.rabbit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class ApplicationConfigReader {
    @Value("${app.exchange.name}")
    private String exchange;
    @Value("${app.queue.name}")
    private String queue;
    @Value("${app.routing.key.name}")
    private String routingKey;


}
