package dao;

import entity.Problem;
import org.apache.ibatis.annotations.Mapper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IProblemDAO {
    void addProblem(Problem problem);
    Problem getProblem(String pid);
    void updateProblem(Problem problem);
    int getTotal();
    List<Problem> getProblemList(int start, int size, String searchFor, String searchContent);
    List<Problem> getProblemListInCompetition(String cid);
}
