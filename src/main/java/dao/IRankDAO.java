package dao;

import entity.Rank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IRankDAO {
    List<Rank> getRankByEmail(String email);
    List<Rank> getRankByCID(String cid);
    void addRank(Rank rank);
    void updateRank(Rank rank);
}
