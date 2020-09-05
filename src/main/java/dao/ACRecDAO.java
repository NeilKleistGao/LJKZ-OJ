package dao;

import entity.ACRec;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ACRecDAO implements IACRecDAO {
    private SqlSession session;
    @EJB
    private BasicDAO basicDAO;
    public ACRecDAO() {
        session = basicDAO.createSession();
    }
    public void addACRec(ACRec rec) {
        session.insert("", rec);
        session.commit();
        session.close();
    }

    public List<String> getACRecByEmail(String email) {
        return session.selectList("getRecordByEmail", email);
    }

    public List<String> getACRecByCompetition(ACRec rec) {
        return session.selectList("getRecordByEmailInCompetition", rec);
    }
}
