package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;

public abstract class BasicDAO {
    protected SqlSession session;

    public BasicDAO() throws Exception {
        String resourcePath = "mybatis-config.xml";
        InputStream stream = Resources.getResourceAsStream(resourcePath);

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
        session = factory.openSession();
    }

    public void commit () {
        session.commit();
        session.close();
    }
}
