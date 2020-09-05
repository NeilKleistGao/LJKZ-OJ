package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import javax.ejb.Singleton;
import java.io.IOException;
import java.io.InputStream;

@Singleton
public class BasicDAO {
    public BasicDAO() { }
    private static SqlSessionFactory sqlSessionFactory;
    public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory == null){
            InputStream inputStream;
            try{
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            }catch (IOException e){
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession openSession(){
        return getSqlSessionFactory().openSession();
    }
}
