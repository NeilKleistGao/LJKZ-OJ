package dao;

import entity.Rank;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RankDAO extends BasicDAO implements IRankDAO {

    @EJB
    private  BasicDAO basicDAO;
    private SqlSession session;

    public RankDAO() throws Exception {
        super();
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
