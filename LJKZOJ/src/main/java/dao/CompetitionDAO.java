package dao;

import entity.Competition;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class CompetitionDAO implements ICompetitionDAO {
    @EJB
    private BasicDAO basicDAO;

    public List<Competition> getCompetitionList(int start, int size, String type) {
        SqlSession session = basicDAO.createSession();
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        map.put("type", type);
        return session.selectList("", map);
    }

    public void addCompetition(Competition competition) {
        SqlSession session = basicDAO.createSession();
        session.insert("addCompetition", competition);
        session.commit();
        session.close();
    }

    public Competition getCompetition(String cid) {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getCompetitionById", cid);
    }

    public void addCompetitionProblem(String pid, String cid) {
        SqlSession session = basicDAO.createSession();
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        session.insert("addCompetitionProblem", map);
        session.commit();
        session.close();
    }

    @Override
    public int getComTotal() {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getComTotal");
    }
}
