package com.zar.sms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@ComponentScan({"com.zar"})
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class,args);
    }
}
