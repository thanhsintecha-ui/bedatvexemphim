package com.example.ticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "com.example.ticketbooking.model", "com.example.ticketbooking.entity" })
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
