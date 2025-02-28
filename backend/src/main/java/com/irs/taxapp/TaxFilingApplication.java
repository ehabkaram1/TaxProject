package com.irs.taxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TaxFilingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxFilingApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Application is running!";
    }

    @GetMapping("/health")
    public String health() {
        return "Healthy " + System.currentTimeMillis();
    }
}