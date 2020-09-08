package com.wlm.kafka.example.consumer;

import com.alibaba.fastjson.JSONObject;
import com.wlm.kafka.example.model.Submission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
public class SimpleConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @KafkaListener(topics = {"topic4"})
    public void listen4(byte[] data) {

        System.out.println(new String(data));
        // 反序列化成submission
        Submission submission= JSONObject.parseObject(data,Submission.class);

        logger.info("接收消息submission,核对下是否和发送过来的一样：{}", submission);

    }
}
