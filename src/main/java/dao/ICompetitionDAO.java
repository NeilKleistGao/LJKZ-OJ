package dao;

import entity.Competition;

import java.util.List;

public interface ICompetitionDAO {
    public List<Competition> getCompetitionList(int start, int size);
    public void addCompetition(Competition competition);
    public Competition getCompetition(String cid);
    public void addCompetitionProblem(String pid, String cid);
}
