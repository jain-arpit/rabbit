package com.demo.rabbit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class UserDetails implements Serializable {
    private Long id;
    private String name;
}
