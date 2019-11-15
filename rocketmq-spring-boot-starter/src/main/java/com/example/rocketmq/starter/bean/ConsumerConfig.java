package com.example.rocketmq.starter.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;

import static com.example.rocketmq.starter.configuration.RocketmqProperties.PREFIX;

/**
 * @author jackie
 * @Title: ConsumerConfig
 * @ProjectName rocketmq-producer-consumer
 * @Description: 消费者配置bean
 * @date 2019/1/15 9:28
 */
@ConfigurationProperties(PREFIX+".consumer")
public class ConsumerConfig {
    /** 客户端实例名 */
    private String instanceName;
    /** 消息模型：集群消费，广播消费 */
    private String messageModel;
    /** 消费线程池最小数量 */
    private Integer consumeThreadMin;
    /** 消费线程池最大数量 */
    private Integer consumeThreadMax;
    /** 批量消费，一次消费多少条消息 */
    private Integer consumeMessageBatchMaxSize;
    /** 长轮询，Consumer拉消息请求在Broker挂起最长时间，单位毫秒 */
    private long brokerSuspendMaxTimeMillis;
    /** 非长轮询，拉消息超时时间，单位毫秒 */
    private long consumerPullTimeoutMillis;
    /** 长轮询，Consumer拉消息请求Broker挂起超过指定时间，客户端认为超时，单位毫秒 */
    private long consumerTimeoutMillisWhenSuspend;
    /** 注册的topic集合 */
//    private Set<String> registerTopics;
    /** 基于拉取模式的消费者组名：pull和push模式不能同时使用 */
    private String consumerPullGroupName;
    /** 基于推送模式的消费者组名：pull和push模式不能同时使用 */
    private String consumerPushGroupName;

    public ConsumerConfig(){
        this.consumeThreadMin=3;
        this.consumeThreadMax=10;
        this.consumeMessageBatchMaxSize=1;
        this.brokerSuspendMaxTimeMillis=20000;
        this.consumerPullTimeoutMillis=10000;
        this.consumerTimeoutMillisWhenSuspend=30000;
        this.messageModel="CLUSTERING";

    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(String messageModel) {
        this.messageModel = messageModel;
    }

    public Integer getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(Integer consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public Integer getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(Integer consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public Integer getConsumeMessageBatchMaxSize() {
        return consumeMessageBatchMaxSize;
    }

    public void setConsumeMessageBatchMaxSize(Integer consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

    public long getBrokerSuspendMaxTimeMillis() {
        return brokerSuspendMaxTimeMillis;
    }

    public void setBrokerSuspendMaxTimeMillis(long brokerSuspendMaxTimeMillis) {
        this.brokerSuspendMaxTimeMillis = brokerSuspendMaxTimeMillis;
    }

    public long getConsumerPullTimeoutMillis() {
        return consumerPullTimeoutMillis;
    }

    public void setConsumerPullTimeoutMillis(long consumerPullTimeoutMillis) {
        this.consumerPullTimeoutMillis = consumerPullTimeoutMillis;
    }

    public long getConsumerTimeoutMillisWhenSuspend() {
        return consumerTimeoutMillisWhenSuspend;
    }

    public void setConsumerTimeoutMillisWhenSuspend(long consumerTimeoutMillisWhenSuspend) {
        this.consumerTimeoutMillisWhenSuspend = consumerTimeoutMillisWhenSuspend;
    }

    public String getConsumerPullGroupName() {
        return consumerPullGroupName;
    }

    public void setConsumerPullGroupName(String consumerPullGroupName) {
        this.consumerPullGroupName = consumerPullGroupName;
    }

    public String getConsumerPushGroupName() {
        return consumerPushGroupName;
    }

    public void setConsumerPushGroupName(String consumerPushGroupName) {
        this.consumerPushGroupName = consumerPushGroupName;
    }
}
