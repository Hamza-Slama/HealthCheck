package com.healthcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HealthCheck {
    public static void main(String[] args) {
        SpringApplication.run(HealthCheck.class, args);
    }
}
