package dao;

import entity.Rank;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RankDAO extends BasicDAO implements IRankDAO {
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
        this.commit();
    }

    public void updateRank(Rank rank) {
        session.update("updateRank", rank);
        this.commit();
    }
}
