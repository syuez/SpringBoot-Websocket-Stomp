package com.syuez.springbootwebsocketstomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootWebSocketStompApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebSocketStompApplication.class, args);
    }

}
