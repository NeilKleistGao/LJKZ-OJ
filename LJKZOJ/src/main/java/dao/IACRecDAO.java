package dao;

import entity.ACRec;
import org.apache.ibatis.annotations.Mapper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IACRecDAO {
    void addACRec(ACRec rec);
    List<String> getACRecByEmail(String email);
    List<String> getACRecByCompetition(ACRec rec);
}
