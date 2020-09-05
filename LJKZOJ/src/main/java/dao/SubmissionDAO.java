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
    @EJB
    private BasicDAO basicDAO;

    public void insert(Submission submission) {
        SqlSession session = basicDAO.createSession();
        session.insert("addSubmission", submission);
        session.commit();
        session.close();
    }

    public void updateState(Submission submission) {
        SqlSession session = basicDAO.createSession();
        session.update("updateState", submission);
        session.commit();
        session.close();
    }

    public List<Submission> getSubmissionList(int start, int size) {
        SqlSession session = basicDAO.createSession();
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        return session.selectList("getSubmissionList", map);
    }
}