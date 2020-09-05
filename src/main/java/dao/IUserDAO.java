package dao;

import entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDAO {
    User getUser(String email);
    void addUser(User user);
    void updateUser(User user);
    boolean exist(String email);
}
