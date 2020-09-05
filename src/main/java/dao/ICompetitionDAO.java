package dao;

import entity.Competition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICompetitionDAO {
    List<Competition> getCompetitionList(int start, int size);
    void addCompetition(Competition competition);
    Competition getCompetition(String cid);
    void addCompetitionProblem(String pid, String cid);
}
