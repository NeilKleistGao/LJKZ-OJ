package dao;

import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Method;

public class UserDAO implements AutoCloseable {
    private EntityManagerFactory factory;
    private EntityManager manager;

    public UserDAO() {
        this.factory = Persistence.createEntityManagerFactory("users");
        this.manager = factory.createEntityManager();
    }

    public void insert(User user) {
        this.manager.getTransaction().begin();
        this.manager.persist(user);
        this.manager.getTransaction().commit();
    }

    public boolean exist(String email) {
        User user = this.getUser(email);
        return user != null;
    }

    public boolean check(User user) {
        User another = this.getUser(user.getEmail());
        return another != null && another.getPassword().equals(user.getPassword());
    }

    public User getUser(String email) {
        return this.manager.find(User.class, email);
    }

    public void updateBasicInfo(String email, String username, String avatar) {
        this.manager.getTransaction().begin();
        User user = this.getUser(email);
        user.setUsername(username);
        user.setAvatar(avatar);

        this.manager.persist(user);
        this.manager.getTransaction().commit();
    }

    public void updatePassword(String email, String password) {
        this.manager.getTransaction().begin();
        User user = this.getUser(email);
        user.setPassword(password);
        this.manager.persist(user);
        this.manager.getTransaction().commit();
    }

    public void addNum(String email, String state) {
        User user = this.getUser(email);
        Method[] methods = User.class.getMethods();
        Method getter = null, setter = null;

        for (Method method : methods) {
            if (method.getName().contains(state)) {
                if (method.getName().contains("get")) {
                    getter = method;
                }
                else {
                    setter = method;
                }
            }
        }

        try {
            int num = (Integer)getter.invoke(user);
            setter.invoke(user, num + 1);

            this.manager.getTransaction().begin();
            this.manager.persist(user);
            this.manager.getTransaction().commit();;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (this.manager != null) {
            this.manager.close();
            this.factory.close();
        }
    }
}
