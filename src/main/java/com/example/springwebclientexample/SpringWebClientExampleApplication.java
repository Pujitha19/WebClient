package com.example.springwebclientexample;


import org.demo.springbootlogger.utils.InternalRestClientLoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringWebClientExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebClientExampleApplication.class, args);
    }

    @Bean
    public InternalRestClientLoggerUtil getInternalRestClientLoggerUtil(){
        return new InternalRestClientLoggerUtil();
    }
}
