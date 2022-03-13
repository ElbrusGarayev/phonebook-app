package com.gara.ibasestep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PhonebookApplication {

    public static void main(String[] args) {
        log.info("application started...");
        SpringApplication.run(PhonebookApplication.class, args);
    }

}
