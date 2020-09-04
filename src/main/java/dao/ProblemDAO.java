package dao;

import entity.Problem;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ProblemDAO extends BasicDAO implements IProblemDAO {
    ProblemDAO() throws Exception {
        super();
    }

    public void addProblem(Problem problem) {
        session.insert("addProblem", problem);
        this.commit();
    }

    public Problem getProblem(String pid) {
        return (Problem) session.selectOne("getProblemById", pid);
    }

    public void updateProblem(Problem problem) {
        session.update("updateProblem", problem);
        session.commit();
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
