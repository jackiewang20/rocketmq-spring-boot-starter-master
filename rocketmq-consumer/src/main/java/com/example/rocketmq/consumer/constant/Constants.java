package com.example.rocketmq.consumer.constant;

import com.example.rocketmq.starter.configuration.RocketmqProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackie
 * @Title: Constants
 * @ProjectName rocketmq-producer-consumer
 * @Description: 常量类
 * @date 2019/1/18 10:55
 */
@Component
public class Constants {
    public static String MQ_TOPIC_1 = "testTopic";
    public static String MQ_TAG_1 = "TagA"; // TagA||TagB
//    public static String MQ_TAG_1 = "TagA||TagB"; //
//    public static List<String> TOPICS = new ArrayList<>();
//    public static List<String> TAGS = new ArrayList<>();

    public static String DEFAULT_ENCODING="utf-8";

}
