package dao;

import entity.Rank;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class RankDAO implements IRankDAO {
    @EJB
    private BasicDAO basicDAO;

    public List<Rank> getRankByEmail(String email) {
        SqlSession session = basicDAO.createSession();
        return session.selectList("getRankByEmail", email);
    }

    public List<Rank> getRankByCID(String cid) {
        SqlSession session = basicDAO.createSession();
        return session.selectList("getRankByCID", cid);
    }

    public void addRank(Rank rank) {
        SqlSession session = basicDAO.createSession();
        session.insert("addRank", rank);
        session.commit();
        session.close();
    }
    public void updateRank(Rank rank) {
        SqlSession session = basicDAO.createSession();
        session.update("updateRank", rank);
        session.commit();
        session.close();
    }

    @Override
    public int getRankCountByCID(String cid) {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getRankCountByCID", cid);
    }

    @Override
    public int getRankCountByCIDLeading(String cid, int acNum, int penalty) {
        SqlSession session = basicDAO.createSession();
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("acNum", acNum);
        map.put("penalty", penalty);
        return session.selectOne("getRankCountByCIDLeading", map);
    }
}
