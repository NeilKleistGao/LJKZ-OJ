package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.ejb.Singleton;
import java.io.IOException;
import java.io.InputStream;

@Singleton
public class BasicDAO {
    private SqlSessionFactory sqlSessionFactory;

    public BasicDAO() {
        try {
            String resourcePath = "mybatis-config.xml";
            InputStream stream = Resources.getResourceAsStream(resourcePath);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SqlSession createSession() {
        return sqlSessionFactory.openSession();
    }
}
