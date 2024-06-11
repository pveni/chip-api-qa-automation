package com.chip.api.qa.automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.chip.api.qa.automation")
public class ApiAutomationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiAutomationApplication.class, args);
    }
}
