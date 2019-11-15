package com.example.rocketmq.producer.controller;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.producer.bean.*;
import com.example.rocketmq.producer.component.TransactionListenerImpl;
import com.example.rocketmq.producer.constant.MQPropertiesUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jackie
 * @Title: ProducerController
 * @ProjectName rocketmq-producer-consumer
 * @Description: 生产MQ消息：
 * 业务需求：
 * （1）系统定义一个流程表：存放服务编排后的每个服务信息，及调用顺序，调用顺序通过序号表示；
 * （2）发送一个流程业务消息：如果流程包含三个服务，需要依次执行本地事务及其它服务，由于服务是
 * 分布式部署，需要将消息投递到mq，消费mq中的消息，然后执行相关业务；
 * （3）业务流程示例：订单服务（订单基础表，订单详情表），减库存服务，加积分服务；
 * （4）先投递减库存消息，在TransactionListenerImpl的executeLocalTransaction执行本地事务方法后，
 * 确认投递消息
 * @date 2019/1/17 11:04
 */
@RequestMapping("producer")
@RestController
public class ProducerController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Autowired
    private TransactionMQProducer transactionMQProducer;
    @Autowired
    private TransactionListenerImpl transactionListenerImpl;

//    @Value("${spring.rocketmq.producer.topic}")
//    private String topic;
//    private static final String[] tags={"TagA","TagB"};

    /** 生产简单消息 */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    public void sendMsg() throws Exception {
        String content = "Hello rocketMQ! " + new Date().toString();
        Message message = null;
        try{
            message = new Message(MQPropertiesUtil.MQ_TOPIC_1, MQPropertiesUtil.MQ_TAG_1,
                    "orderId-001", content.getBytes());
//          Message message = new Message(topic, tags[0], "orderId-001", content.getBytes());

            Message finalMessage = message;
            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    // 发送成功业务
                    LOGGER.info("[producer]发送消息成功，消息信息：{}",sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    // 发送失败业务：重试三次
                    LOGGER.info("[producer]发送消息失败，消息信息：{}，异常信息{}：", finalMessage,throwable);
                }
            });
        } catch(Exception e) {
            LOGGER.error("[producer]发送消息异常：{}：" ,e);
        }
    }

    /** 生产事务消息 */
    @RequestMapping(value = "sendTransactionMsg")
    public String sendTransactionMsg() throws Exception {
        SendResult sendResult = null;
        Message message=null;
        transactionMQProducer.setTransactionListener(transactionListenerImpl);


        /**
         * 必传属性值：
         * 订单号，商品id，userId，消息id，积分，库存，业务类型
         */
        String orderNum = "orderNo-gen-"+new Date();
        /**
         * 消息业务唯一key，用于识别业务消息，是否重新消费等
         */
        String messageBizUniqueKey=orderNum;

        // =================== 需要投递mq的消息内容 ====================
        String transMessage = ""; // "Hello rocketMQ! This is transaction message. " + new Date().toString();

        // 库存信息：根据productId减库存；当前商品id为2的剩余库存为500
        Inventory inventory = new Inventory();
        inventory.setNumber(1); // 减库存
        inventory.setProductId(2);
        inventory.setBizType("inventoryService");

        transMessage = JSON.toJSONString(inventory);

        // =================== 本地事务相关参数 ====================
        OrderExt orderExt = new OrderExt();
        // 订单基础
        Order order = new Order();
        order.setOrderNo(orderNum);
        order.setCreateTime(new Date());
        order.setOrderAmount(new BigDecimal(1999));
        order.setOrderStatus(1);
        order.setShopId(1);
        order.setUserId(1);
        orderExt.setOrder(order);

        // 订单详情
        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setOrderId(1L); // 需要添加订单后查询出来
        orderDetail.setNumber(1);
        orderDetail.setProductId(2);// product_no值p-0002
        orderDetail.setProductModeDesc("blue,250g");
        orderDetail.setProductModeParams("tx商品类型参数");
        orderDetail.setProductName("小米");
        orderDetail.setProductPrice(new BigDecimal(1999));
        orderDetail.setRemark("备注");
        orderDetail.setSubtotal(new BigDecimal(1999));
        orderExt.setOrderDetail(orderDetail);

        try{
            message = new Message(MQPropertiesUtil.MQ_TOPIC_1, MQPropertiesUtil.MQ_TAG_1,
                    messageBizUniqueKey, transMessage.getBytes());
            //发送减库存事务消息到mq：sendMessageInTransaction(投递的消息，本地执行事务的参数)
            sendResult = transactionMQProducer.sendMessageInTransaction(message, orderExt);
            LOGGER.info("[producer-send-transaction-message]投递事务消息成功，消息信息：{}", message);
        } catch (Exception e) {
            LOGGER.error("[producer-send-transaction-message]发送事务消息异常：", e);
        }
        return sendResult.toString();
    }

}
