package com.gbtec.email.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmailApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailApiApplication.class, args);
    }
}
