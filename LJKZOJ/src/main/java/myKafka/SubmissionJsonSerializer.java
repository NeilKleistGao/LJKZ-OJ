package myKafka;
import org.apache.kafka.common.serialization.Serializer;
import com.alibaba.fastjson.JSON;
import java.util.Map;

public class SubmissionJsonSerializer implements Serializer<SubmissionProducer> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, SubmissionProducer data) {
        return JSON.toJSONBytes(data);
    }

    @Override
    public void close() {

    }
}