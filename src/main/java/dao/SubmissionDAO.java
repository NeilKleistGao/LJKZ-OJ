package dao;

import entity.Submission;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SubmissionDAO implements ISubmissionDAO{
    private SqlSession session;
    @EJB
    private BasicDAO basicDAO;
    public SubmissionDAO() {
        session = basicDAO.createSession();
    }

    public void insert(Submission submission) {
        session.insert("addSubmission", submission);
        session.commit();
        session.close();
    }

    public void updateState(Submission submission) {
        session.update("updateState", submission);
        session.commit();
        session.close();
    }

    public List<Submission> getSubmissionList(int start, int size) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        return session.selectList("getSubmissionList", map);
    }
}
