package dao;

import entity.Submission;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SubmissionDAO extends BasicDAO implements ISubmissionDAO {
    public SubmissionDAO() throws Exception {
        super();
    }

    public void insertSubmission(Submission submission) {
        session.insert("addSubmission", submission);
        this.commit();
    }

    public void updateState(Submission submission) {
        session.update("updateState", submission);
        this.commit();
    }

    public List<Submission> getSubmissionList(int start, int size) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        return session.selectList("getSubmissionList", map);
    }
}
