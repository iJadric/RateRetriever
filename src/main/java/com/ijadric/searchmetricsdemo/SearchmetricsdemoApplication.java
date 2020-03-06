package com.ijadric.searchmetricsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SearchmetricsdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchmetricsdemoApplication.class, args);
    }

}
