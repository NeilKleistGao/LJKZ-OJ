package dao;

import org.apache.ibatis.session.SqlSession;

import javax.ejb.Local;

@Local
public interface IBasicDAO {
    SqlSession createSession();
}
