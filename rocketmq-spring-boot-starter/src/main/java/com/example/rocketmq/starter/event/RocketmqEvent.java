package com.example.rocketmq.starter.event;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.io.UnsupportedEncodingException;

/**
 * @author jackie
 * @Title: RocketmqEvent
 * @ProjectName rocketmq-producer-consumer
 * @Description: 消息事件，包装MessageExt和DefaultMQPushConsumer的对象；
 * 基于spring事件传播机制的事件类RocketmqEvent，用来定义上面的consumer接收到消息后的发布的事件。
 * @date 2019/1/16 10:05
 */
//@EqualsAndHashCode(callSuper = false) // 如果当前类继承了父类，排除调用父类的super()方法
//@Slf4j
//@Data
public class RocketmqEvent extends ApplicationEvent {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    private DefaultMQPushConsumer consumer;
    private MessageExt messageExt;
    private String msgId;
    private String topic;
    private String tag;
    private byte[] body;

    public RocketmqEvent(MessageExt msg, DefaultMQPushConsumer consumer) {
        super(msg);
        this.consumer = consumer;
        this.messageExt = msg;
        this.msgId = msg.getMsgId();
        this.topic = msg.getTopic();
        this.tag = msg.getTags();
        this.body = msg.getBody();
    }

    public String getMsg(){
        try {
            return new String(this.body, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("MQ消息编码异常：",e);
            return null;
        }
    }

    public String getMsg(String code){
        try {
            return new String(this.body, code);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("MQ消息编码异常：",e);
            return null;
        }
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public MessageExt getMessageExt() {
        return messageExt;
    }

    public void setMessageExt(MessageExt messageExt) {
        this.messageExt = messageExt;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
