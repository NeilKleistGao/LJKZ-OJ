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
    private SqlSession session;
    @EJB
    private BasicDAO basicDAO;

    public void addProblem(Problem problem) {
        session = basicDAO.createSession();
    }

    public Problem getProblem(String pid) {
        return (Problem) session.selectOne("getProblemById", pid);
    }

    public void updateProblem(Problem problem) {
        session.update("updateProblem", problem);
        session.commit();
        session.close();
    }

    public int getTotal() {
        int total = session.selectOne("getTotal");
        return total;
    }

    public List<Problem> getProblemList(int start, int size, String searchFor, String searchContent) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        map.put("for", searchFor);
        map.put("content", searchContent);
        return session.selectList("getProblemList", map);
    }
}
