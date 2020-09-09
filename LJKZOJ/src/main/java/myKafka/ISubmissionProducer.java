package myKafka;

import entity.Submission;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ISubmissionProducer {
    void send();
}
