package com.example.rocketmq.starter.configuration;

import com.example.rocketmq.starter.bean.ConsumerConfig;
import com.example.rocketmq.starter.bean.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.example.rocketmq.starter.configuration.RocketmqProperties.PREFIX;


/**
 * @author jackie
 * @Title: RocketmqProperties
 * @ProjectName rocketmq-producer-consumer
 * @Description: 自定义MQ属性
 * @date 2019/1/14 17:06
 */
@ConfigurationProperties(PREFIX)
public class RocketmqProperties {
    public static final String PREFIX = "spring.rocketmq";

    /** topic和tags配置（topic:tag1||tag2)  */
    private List<String> topicAndTags;

    /** rocketMQ集群地址列表 */
    private String namesrvAddr;
    /** 当前客户端ip地址 */
    private String currentClientIpAddress;
    private ProducerConfig producer;
    private ConsumerConfig consumer;
    /** 消息最大值，默认4M */
    private Integer maxMessageSize=1024*1024*4;
    /** 消息发送超时时间，默认3秒 */
    private Integer sendMsgTimeout=3;
    /** 消息发送失败重试次数，默认2次 */
    private Integer retryTimesWhenSendFailed=2;


    public List<String> getTopicAndTags() {
        return topicAndTags;
    }

    public void setTopicAndTags(List<String> topicAndTags) {
        this.topicAndTags = topicAndTags;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getCurrentClientIpAddress() {
        return currentClientIpAddress;
    }

    public void setCurrentClientIpAddress(String currentClientIpAddress) {
        this.currentClientIpAddress = currentClientIpAddress;
    }

    public ProducerConfig getProducer() {
        return producer;
    }

    public void setProducer(ProducerConfig producer) {
        this.producer = producer;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public Integer getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }
}
