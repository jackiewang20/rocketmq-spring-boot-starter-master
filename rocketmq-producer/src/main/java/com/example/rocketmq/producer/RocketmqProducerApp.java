package com.example.rocketmq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jackie
 * @Title: RocketmqProducerApp
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/14 16:02
 */
@SpringBootApplication(scanBasePackages = {"com.example.rocketmq.producer", "com.example.rocketmq.starter"})
public class RocketmqProducerApp {

    public static void main(String[] args){
        SpringApplication.run(RocketmqProducerApp.class, args);
    }

}
