package myKafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import javax.ejb.Singleton;

@Singleton
public class SubmissionProducer implements ISubmissionProducer {
    KafkaProducer<String, Submission> producer;
    public SubmissionProducer() {
        Properties properties = new Properties();
        //kafka服务
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("acks","0");
        properties.put("group.id","org.example");
        //重试次数
        properties.put("retries","0");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","myKafka.SubmissionJsonSerializer");
        producer = new KafkaProducer<>(properties);
    }

    @Override
    public void send(String topic, String key, Submission value){
        producer.send(new ProducerRecord<String, Submission>(topic, value));
    }
}
