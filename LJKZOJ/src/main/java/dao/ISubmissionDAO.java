package dao;

import entity.Submission;
import org.apache.ibatis.annotations.Mapper;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;

@Local
public interface ISubmissionDAO {
    void insert(Submission submission);
    void updateState(String sid);
    List<HashMap<String, Object>> getSubmissionList(int start, int size);
    int getSubmitTotal(String pid);
    int getACtotal(String pid);
    List<String> getUserSubmissionList(String email);
    List<String> getUserACList(String email);
    int getAllSubmitTotal();
    Submission getSubmission(String sid);
}
