package com.projetobetha.dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DevApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
