package com.reviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReviewerApplicationBoot {

    public static void main(String[] args) {
        Thread springBootThread = new Thread(() -> SpringApplication.run(ReviewerApplicationBoot.class, args));
        springBootThread.start();
    }
}