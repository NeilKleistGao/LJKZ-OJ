package dao;

import entity.Problem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProblemDAO {
    void addProblem(Problem problem);
    Problem getProblem(String pid);
    void updateProblem(Problem problem);
    int getTotal();
    List<Problem> getProblemList(int start, int size, String searchFor, String searchContent);
}
