package kafka;

import entity.Problem;
import org.apache.ibatis.session.SqlSession;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class SubmissionProducer implements ISubmissionProducer {
    @EJB

    public void properties() {
        Properties properties = new Properties();
        //kafka服务
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("acks","0");
        properties.put("group.id","org.example");
        //重试次数
        properties.put("retries","0");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","SubmissionJsonSerializer");

        KafkaProducer<String, SubmissionProducer> producer = new KafkaProducer<>(properties);

    }
}
