package dao;

import entity.User;

public interface IUserDAO {
    public User getUser(String email);
    public void addUser(User user);
    public void updateUser(User user);
}
