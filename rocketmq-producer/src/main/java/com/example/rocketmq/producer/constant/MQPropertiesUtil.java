package com.example.rocketmq.producer.constant;

import com.example.rocketmq.starter.configuration.RocketmqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jackie
 * @Title: ConstantUtil
 * @ProjectName rocketmq-producer-consumer
 * @Description: MQ属性工具类，加工部分属性：构造函数中初始化，不能获取带注解的类属性值。
 * @date 2019/1/18 10:55
 */
//@Component // @Configuration包含@Component注解
@Configuration
@EnableConfigurationProperties(RocketmqProperties.class)
//@ConditionalOnProperty(prefix = PREFIX, value="namesrvAddr") // 条件加载配置属性，如果满足条件，实例化当前类
public class MQPropertiesUtil {
    @Autowired
    private RocketmqProperties properties;

    public static String MQ_TOPIC_1;
    public static String MQ_TAG_1;
    public static List<String> TOPICS = new ArrayList<>();
    public static List<String> TAGS = new ArrayList<>();

    //
//    public ConstantUtil(){
//        List<String> subscribeTopicTagList = properties.getConsumer().getSubscribeTopicTag();
//        for (String subscribeTopicTag : subscribeTopicTagList){
//            TOPICS.add(subscribeTopicTag.split(":")[0]);
//            TAGS.add(subscribeTopicTag.split(":")[1]);
//        }
//
//        MQ_TOPIC_1 = TOPICS.get(0);
//        MQ_TAG_1 = TAGS.get(0);
//    }

    public void test(){
        System.err.println("[getSubscribeTopicTag]="+properties.getTopicAndTags());
        System.err.println("[TOPICS]="+TOPICS);
        System.err.println("[TAGS]="+TAGS);
        System.err.println("[MQ_TOPIC_1]="+MQ_TOPIC_1);
        System.err.println("[MQ_TAG_1]="+MQ_TAG_1);
    }

    public void init() {
        if(TOPICS!=null &&TOPICS.size()<=0) {
            List<String> subscribeTopicTagList = properties.getTopicAndTags();
            for (String subscribeTopicTag : subscribeTopicTagList) {
                TOPICS.add(subscribeTopicTag.split(":")[0]);
                TAGS.add(subscribeTopicTag.split(":")[1]);
            }

            MQ_TOPIC_1 = TOPICS.get(0);
            // 获取第0个TOPIC下的第0个TAG
            MQ_TAG_1= TAGS.get(0).replace("||", ":").split(":")[0];
//            String t="";
        }
    }


}
