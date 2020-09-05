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
    private  BasicDAO basicDAO;
    private  SqlSession session;
    public CompetitionDAO(BasicDAO basicDAO) {
        session = basicDAO.openSession();
    }

    public List<Competition> getCompetitionList(int start, int size) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        return session.selectList("", map);
    }

    public void addCompetition(Competition competition) {
        session.insert("addCompetition", competition);
        session.commit();
        session.close();
    }

    public Competition getCompetition(String cid) {
        return session.selectOne("getCompetitionById", cid);
    }

    public void addCompetitionProblem(String pid, String cid) {
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        session.insert("addCompetitionProblem", map);
        session.commit();
        session.close();
    }
}
