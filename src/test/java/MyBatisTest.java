import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class MyBatisTest {

    public static void main(String[] args) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream stream = Resources.getResourceAsStream(resource);

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession session = factory.openSession();

        User user = new User();
        user.setEmail("test");
        user.setPassword("test");
        user.setUsername("test");
        session.insert("addUser", user);

        session.commit();
        session.close();
    }

    @Test
    public void test(){

    }
}
