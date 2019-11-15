package com.example.rocketmq.producer.bean;

import com.example.rocketmq.producer.common.EnumTransCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jackie
 * @Title: EnumTransCodeTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/22 15:16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EnumTransCodeTest {

    @Test
    public void getTextByCode() {
//        String type = EnumTransCode.getTypeByCode(1).toString();
//        System.err.println("========="+type);

        String content = EnumTransCode.getContentByCode(1).toString();
        System.err.println("========="+content);

        System.err.println( EnumTransCode.CODE_COMMIT_MESSAGE.getCode());
    }
}