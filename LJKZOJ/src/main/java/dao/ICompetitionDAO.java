package dao;

import entity.Competition;
import org.apache.ibatis.annotations.Mapper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ICompetitionDAO {
    List<Competition> getCompetitionList(int start, int size, String type);
    void addCompetition(Competition competition);
    Competition getCompetition(String cid);
    void addCompetitionProblem(String pid, String cid);
    int getComTotal();
}
