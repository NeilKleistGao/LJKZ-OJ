package Mapper;

import org.apache.ibatis.annotations.Mapper;
import pojo.RankPojo;

@Mapper
public interface RankMapper {
    void updateRank(RankPojo rank);
    RankPojo getRank(RankPojo rank);
}
