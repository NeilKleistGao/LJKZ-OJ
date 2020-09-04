package dao;

import entity.User;

import javax.ejb.Stateless;

@Stateless
public class UserDAO extends BasicDAO implements IUserDAO {

    public UserDAO() throws Exception {
        super();
    }

    public User getUser(String email) {
        return session.selectOne("getUserByEmail", email);
    }

    public void addUser(User user) {
        session.insert("addUser", user);
        this.commit();
    }

    public void updateUser(User user) {
        session.update("updateUser", user);
        this.commit();
    }


}
