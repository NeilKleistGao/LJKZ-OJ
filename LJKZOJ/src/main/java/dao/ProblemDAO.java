package dao;

import entity.Problem;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ProblemDAO implements IProblemDAO {
    @EJB
    private BasicDAO basicDAO;

    public void addProblem(Problem problem) {
        SqlSession session = basicDAO.createSession();
        session.insert("addProblem", problem);
        session.commit();
        session.close();
    }

    public Problem getProblem(String pid) {
        SqlSession session = basicDAO.createSession();
        return (Problem) session.selectOne("getProblemById", pid);
    }

    public void updateProblem(Problem problem) {
        SqlSession session = basicDAO.createSession();
        session.update("updateProblem", problem);
        session.commit();
        session.close();
    }

    public int getTotal() {
        SqlSession session = basicDAO.createSession();
        int total = session.selectOne("getTotal");
        return total;
    }

    public List<Problem> getProblemList(int start, int size, String searchFor, String searchContent) {
        SqlSession session = basicDAO.createSession();
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        map.put("for", searchFor);
        map.put("content", searchContent);
        return session.selectList("getProblemList", map);
    }

    @Override
    public List<Problem> getProblemListInCompetition(int cid) {
        return null;
    }
}
