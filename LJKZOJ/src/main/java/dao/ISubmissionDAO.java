package dao;

import entity.Submission;
import org.apache.ibatis.annotations.Mapper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ISubmissionDAO {
    void insert(Submission submission);
    void updateState(Submission submission);
    List<Submission> getSubmissionList(int start, int size);
}
