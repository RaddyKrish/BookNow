package com.krish.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "application.properties")
public class BookNowApp{
    public static void main (String [] args){
        ConfigurableApplicationContext context = SpringApplication.run(BookNowApp.class, args);
    }

}
