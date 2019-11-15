package com.example.rocketmq.consumer.component;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.rocketmq.consumer.bean.Integral;
import com.example.rocketmq.consumer.bean.Inventory;
import com.example.rocketmq.consumer.constant.MQPropertiesUtil;
import com.example.rocketmq.consumer.service.IntegralService;
import com.example.rocketmq.consumer.service.InventoryService;
import com.example.rocketmq.starter.configuration.RocketmqProperties;
import com.example.rocketmq.starter.event.RocketmqEvent;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jackie
 * @Title: ComsumerComponent
 * @ProjectName rocketmq-producer-consumer
 * @Description: MQ PUSH模式推送消息，消费者消费MQ消息
 * @date 2019/1/17 11:43
 */
@Configuration
@EnableConfigurationProperties(RocketmqProperties.class)
@Component
public class ConsumerComponent {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private RocketmqProperties properties;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private IntegralService integralService;

    /**
     * 客户端监听PUSH的消息：监听第0个TOPIC下的第0个TAG；
     *
     * 说明：@EventListener condition中属性赋值的几种方式。
     * （1）方法1:使用静态代码写死；
     * （2）方法2：使用带注解的常量类属性；
     * （3）方法3（推荐）：使用动态加载带注解类的属性，动态加载的类获取MQ配置文件属性，
     * 并且通过带有@PostConstruct的方法进行初始化赋值操作；
     * （4）方法4：同方法3；
     *
     * @param event
     */
//    @EventListener(condition = "#event.topic=='testTopic' && #event.tag=='TagA'") // 方法1:使用静态代码写死
//    @EventListener(condition = "#event.topic==@constants.MQ_TOPIC_1 && #event.tag==@constants.MQ_TAG_1") // 方法2：使用带注解的常量类属性
    @EventListener(condition = "#event.topic==@MQPropertiesUtil.MQ_TOPIC_1 && #event.tag==@MQPropertiesUtil.MQ_TAG_1") // 方法3：使用动态加载带注解类的属性；
//    @EventListener(condition = "#event.topic==@MQPropertiesUtil.TOPICS[0] && #event.tag==@MQPropertiesUtil.TAGS[0].replace(\"||\", \":\").split(\":\")[0]") // 方法4：使用动态加载带注解类的属性；
    public void RocketmqMsgListen(RocketmqEvent event) {
        LOGGER.error("主题：{},TAG:{}", MQPropertiesUtil.MQ_TOPIC_1 ,MQPropertiesUtil.MQ_TAG_1);
        DefaultMQPushConsumer consumer = event.getConsumer();
        try {
//            if(true){
//                throw new RuntimeException("[consumer]消费PUSH消息，抛异常测试，支持集群模式。");
//            }

            /** 解析事务消息， */
            // 将消息转换为json对象，如果转型失败，说明该消息不符合协议，直接返回
            JSONObject jsonObject =null;
            try{
                jsonObject = JSONObject.parseObject(event.getMsg());

                if(jsonObject == null) {
                    return;
                }
            } catch (ClassCastException e){
//                LOGGER.error("[consumer]JSON转换类型错误：{}", e);
                return;
            } catch (JSONException e2) {
//                LOGGER.error("[consumer]JSON格式无效：{}", e2);
                return;
            } catch (Exception e3) {
//                LOGGER.error("[consumer]JSON解析失败：{}", e3);
                return;
            }

            try{

                /**
                 * 根据消息类型，执行相应的业务操作：
                 * （1）减库存业务；
                 * （2）加积分业务；
                 */
                if("inventoryService".equals(jsonObject.getString("bizType"))) {
                    Inventory inventory = JSONObject.parseObject(event.getMsg(), Inventory.class);
                    Integer result = inventoryService.subtractInventoryByProductId(inventory);
                    if(result != null && result > 0) {
                        System.err.println("================= 减库存成功 ===================");
                    }else {
                        System.err.println("================= 减库存失败 ===================");
                    }
                } else if("integralService".equals(jsonObject.getString("bizType"))) {
                    Integral integral = JSONObject.parseObject(event.getMsg(), Integral.class);
                    Integer result = integralService.addIntegralWithUserId(integral);
                    if(result != null && result > 0) {
                        System.err.println("================= 加积分成功 ===================");
                    } else {
                        System.err.println("================= 加积分失败 ===================");
                    }
                }

            } catch (Exception e){
                LOGGER.error("[consumer]消息消费完成，执行本地业务失败：{}", e);
            }

            LOGGER.error("======================================" +
                    "[consumer]成功消费PUSH消息。消息id：{},消息内容{}：" ,event.getMsgId() ,event.getMsg());
            // 业务操作 TODO
        } catch (Exception e) {
            // 客户端消费消息失败，会调用consumer.sendMessageBack()重复尝试消费3次
            if (event.getMessageExt().getReconsumeTimes() < 3) {
                try {
                    consumer.sendMessageBack(event.getMessageExt(), 2);
                    LOGGER.info("[consumer]消费PUSH消息，重试消费3-{}次，消息详细信息：{}。",
                            event.getMessageExt().getReconsumeTimes()+1, event.getMsg());
                } catch (Exception e1) {
                    // 消息消费失败，进行日志记录
                    LOGGER.error("[consumer]消费PUSH消息，重试消费3-{}次消费失败，消息详细信息：{}。",
                            event.getMessageExt().getReconsumeTimes()+1, event.getMsg());
                }
            } else {
                LOGGER.error("[consumer]消费PUSH消息，客户端多次尝试消费失败；消息内容：{}，异常信息{}：", event.getMsg(), e);
                // TODO 将当前消息持久化，后续重新发送消息并消费

            }
        }
    }


}
