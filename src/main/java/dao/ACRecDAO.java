package dao;

import entity.ACRec;

import java.util.List;

public class ACRecDAO extends BasicDAO implements IACRecDAO {

    public ACRecDAO() throws Exception {
        super();
    }

    public void addACRec(ACRec rec) {
        session.insert("", rec);
        this.commit();
    }

    public List<String> getACRecByEmail(ACRec rec) {
        return session.selectList("getRecordByEmail", rec);
    }

    public List<String> getACRecByCompetition(ACRec rec) {
        return session.selectList("getRecordByEmailInCompetition", rec);
    }
}
