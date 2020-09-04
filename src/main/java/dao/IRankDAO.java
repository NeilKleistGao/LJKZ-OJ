package dao;

import entity.Rank;

import java.util.List;

public interface IRankDAO {
    public List<Rank> getRankByEmail(String email);
    public List<Rank> getRankByCID(String cid);
    public void addRank(Rank rank);
    public void updateRank(Rank rank);
}
