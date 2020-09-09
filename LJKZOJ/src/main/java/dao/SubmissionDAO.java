package dao;

import entity.Submission;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;
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

    public List<HashMap<String, Object>> getSubmissionList(int start, int size) {
        SqlSession session = basicDAO.createSession();
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        return session.selectList("getSubmissionList", map);
    }

    @Override
    public int getSubmitTotal(String pid) {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getSubmitTotal", pid);
    }

    @Override
    public int getACtotal(String pid) {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getACTotal", pid);
    }

    @Override
    public List<String> getUserSubmissionList(String email) {
        SqlSession session = basicDAO.createSession();
        return session.selectList("getUserSubmissionList", email);
    }

    @Override
    public List<String> getUserACList(String email) {
        SqlSession session = basicDAO.createSession();
        return session.selectList("getUserACList", email);
    }

    @Override
    public int getAllSubmitTotal() {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getAllSubmitTotal");
    }

    @Override
    public Submission getSubmission(String email, String pid, Date submitTime) {
        SqlSession  session = basicDAO.createSession();
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("pid", pid);
        map.put("submitTime", submitTime);
        return session.selectOne("getSubmission", map);
    }
}
