package com.example.rocketmq.starter.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.example.rocketmq.starter.configuration.RocketmqProperties.PREFIX;

/**
 * @author jackie
 * @Title: ProducerConfig
 * @ProjectName rocketmq-producer-consumer
 * @Description: 生产者配置bean
 * @date 2019/1/15 9:29
 */
@ConfigurationProperties(PREFIX+".producer")
public class ProducerConfig {
    /** 实例名 */
    private String instanceName;
    /** 事务实例名 */
    private String tranInstanceName;
    /** 最小线程数 */
    private Integer threadPoolMinSize=1;
    /** 最大线程数 */
    private Integer threadPoolMaxSize=2;
    /** 空闲线程回收间隔时间，单位为秒 */
    private Integer keepAliveTime=30;
    /** 线程池队列容量 */
    private Integer blockingQueueCapacity=2000;
    private Boolean vipChannelEnabled=false;
    /** producer组名 */
    private String producerGroupName;
    /** 带事务的producer组名 */
    private String tranProducerGroupName;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getTranInstanceName() {
        return tranInstanceName;
    }

    public void setTranInstanceName(String tranInstanceName) {
        this.tranInstanceName = tranInstanceName;
    }

    public Integer getThreadPoolMinSize() {
        return threadPoolMinSize;
    }

    public void setThreadPoolMinSize(Integer threadPoolMinSize) {
        this.threadPoolMinSize = threadPoolMinSize;
    }

    public Integer getThreadPoolMaxSize() {
        return threadPoolMaxSize;
    }

    public void setThreadPoolMaxSize(Integer threadPoolMaxSize) {
        this.threadPoolMaxSize = threadPoolMaxSize;
    }

    public Integer getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Integer keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getBlockingQueueCapacity() {
        return blockingQueueCapacity;
    }

    public void setBlockingQueueCapacity(Integer blockingQueueCapacity) {
        this.blockingQueueCapacity = blockingQueueCapacity;
    }

    public Boolean getVipChannelEnabled() {
        return vipChannelEnabled;
    }

    public void setVipChannelEnabled(Boolean vipChannelEnabled) {
        this.vipChannelEnabled = vipChannelEnabled;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName) {
        this.producerGroupName = producerGroupName;
    }

    public String getTranProducerGroupName() {
        return tranProducerGroupName;
    }

    public void setTranProducerGroupName(String tranProducerGroupName) {
        this.tranProducerGroupName = tranProducerGroupName;
    }
}
