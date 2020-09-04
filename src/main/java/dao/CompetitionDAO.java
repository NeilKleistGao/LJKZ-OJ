package dao;

import entity.Competition;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class CompetitionDAO extends BasicDAO implements ICompetitionDAO {
    public CompetitionDAO() throws Exception {
        super();
    }

    public List<Competition> getCompetitionList(int start, int size) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        return session.selectList("", map);
    }

    public void addCompetition(Competition competition) {
        session.insert("addCompetition", competition);
        this.commit();
    }

    public Competition getCompetition(String cid) {
        return session.selectOne("getCompetitionById", cid);
    }

    public void addCompetitionProblem(String pid, String cid) {
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        session.insert("addCompetitionProblem", map);
        this.commit();
    }
}
