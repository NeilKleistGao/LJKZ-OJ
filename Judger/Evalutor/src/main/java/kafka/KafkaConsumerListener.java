package com.wlm.kafka.example.listenner;

import com.alibaba.fastjson.JSONObject;
import com.wlm.kafka.example.model.Submission;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;


public class KafkaConsumerListener {


//    @KafkaListener(topics = "testTopic01")
//    public void listen01(ConsumerRecord<String,String> consumerRecord){
//        System.out.println("开始消费testTopic01的消息");
//        System.out.println("消费者线程："+Thread.currentThread().getName()+"[ 消息 来自kafkatopic："+consumerRecord.topic()+",分区："+consumerRecord.partition() +" ，委托时间："+consumerRecord.timestamp()+"]消息内容如下：");
//        System.out.println(consumerRecord.value());
//    }
//
//    @KafkaListener(topics = "testTopic02")
//    public void listen02(ConsumerRecord<String,String> consumerRecord){
//        System.out.println("开始消费testTopic02的消息");
//        System.out.println(consumerRecord.value());
//    }
//
//    /**
//     * 消费 某个topic 下指定分区的消息
//     */
//    @KafkaListener(topicPartitions = {@TopicPartition(topic = "liuzebiao",partitions = {"1"})})
//    public void topicMessage(ConsumerRecord<?, ?> record,String content){
//        System.out.println("消息:"+ content);
//        System.out.println("消息被消费------>Topic:"+ record.topic() + ",------>Value:" + record.value() +",------>Partition:" + record.partition());
//    }



    @KafkaListener(topics = "test")
    public void listen04(ConsumerRecord<String,byte[]> consumerRecord){
        System.out.println("开始消费test的消息");
        System.out.println(consumerRecord.value());
        byte[] data = consumerRecord.value();

                // 字节数组转string数据
        System.out.println(new String(data));
        // 反序列化成submission

        Submission submission= JSONObject.parseObject(data,Submission.class);
        System.out.println("接收消息submission,核对下是否和发送过来的一样： "+submission);
    }
}