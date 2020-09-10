package myKafka;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import com.alibaba.fastjson.JSON;
import java.util.Map;

public class SubmissionJsonSerializer implements Serializer<Submission> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Submission submission) {
        return JSON.toJSONBytes(submission);
    }

    @Override
    public void close() {

    }
}