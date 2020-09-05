package dao;

import entity.Rank;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RankDAO implements IRankDAO {
    private SqlSession session;
    @EJB
    private BasicDAO basicDAO;

    public RankDAO() {
        session = basicDAO.createSession();
    }

    public List<Rank> getRankByEmail(String email) {
        return session.selectList("getRankByEmail", email);
    }

    public List<Rank> getRankByCID(String cid) {
        return session.selectList("getRankByCID", cid);
    }

    public void addRank(Rank rank) {
        session.insert("addRank", rank);
        session.commit();
        session.close();
    }
    public void updateRank(Rank rank) {
        session.update("updateRank", rank);
        session.commit();
        session.close();
    }
}
