package dao;

import entity.User;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserDAO implements IUserDAO{

    @EJB
    private  BasicDAO basicDAO;
    private SqlSession session;
    public UserDAO(){
        session = basicDAO.openSession();
    }

    public User getUser(String email) {
        return session.selectOne("getUserByEmail", email);
    }

    public void addUser(User user) {
        session.insert("addUser", user);
        session.commit();
        session.close();
    }

    public void updateUser(User user) {
        session.update("updateUser", user);
        session.commit();
        session.close();
    }

    @Override
    public boolean exist(String email) {
        return false;
    }
}
