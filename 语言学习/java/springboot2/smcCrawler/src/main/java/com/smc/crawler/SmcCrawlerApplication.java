package com.smc.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmcCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmcCrawlerApplication.class, args);
    }

}
