package dao;

import entity.User;
import org.apache.ibatis.session.SqlSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserDAO implements IUserDAO{
    @EJB
    private BasicDAO basicDAO;

    public User getUser(String email) {
        SqlSession session = basicDAO.createSession();
        return session.selectOne("getUserByEmail", email);
    }

    public void addUser(User user) {
        SqlSession session = basicDAO.createSession();
        session.insert("addUser", user);
        session.commit();
        session.close();
    }

    public void updateUser(User user) {
        SqlSession session = basicDAO.createSession();
        session.update("updateUser", user);
        session.commit();
        session.close();
    }
}
