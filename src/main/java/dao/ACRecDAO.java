package dao;

import entity.ACRec;
import lombok.Data;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import java.util.List;

@Data
public class ACRecDAO implements IACRecDAO {

    @EJB
    private BasicDAO basicDAO;
    private SqlSession session;
    public ACRecDAO() throws Exception {
        session = basicDAO.openSession();
    }
    public void addACRec(ACRec rec) {
        session.insert("", rec);
        session.commit();
        session.close();
    }

    public List<String> getACRecByEmail(ACRec rec) {
        return session.selectList("getRecordByEmail", rec);
    }

    public List<String> getACRecByCompetition(ACRec rec) {
        return session.selectList("getRecordByEmailInCompetition", rec);
    }
}
