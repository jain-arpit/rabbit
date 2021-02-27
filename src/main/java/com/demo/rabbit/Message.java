package com.demo.rabbit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class Message implements Serializable {
    private String message;
    private String routingKey;
    private String exchange;
    private String department;
}
