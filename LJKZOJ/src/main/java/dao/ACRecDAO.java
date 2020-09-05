package dao;

import entity.ACRec;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ACRecDAO implements IACRecDAO {
    @EJB
    private BasicDAO basicDAO;
    public void addACRec(ACRec rec) {
        SqlSession session = basicDAO.createSession();
        session.insert("", rec);
        session.commit();
        session.close();
    }

    public List<String> getACRecByEmail(String email) {
        SqlSession session = basicDAO.createSession();
        return session.selectList("getRecordByEmail", email);
    }

    public List<String> getACRecByCompetition(ACRec rec) {
        SqlSession session = basicDAO.createSession();
        return session.selectList("getRecordByEmailInCompetition", rec);
    }
}
