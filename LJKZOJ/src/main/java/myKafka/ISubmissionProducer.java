package myKafka;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ISubmissionProducer {
    void send(String topic, String key, Submission value);
}
