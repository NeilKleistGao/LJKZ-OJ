package dao;

import entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.ejb.Local;

@Local
public interface IUserDAO {
    User getUser(String email);
    void addUser(User user);
    void updateUser(User user);
}
