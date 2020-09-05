package dao;

import entity.Problem;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IProblemDAO {
    public void addProblem(Problem problem);
    public Problem getProblem(String pid);
    public void updateProblem(Problem problem);
    public int getTotal();
    public List<Problem> getProblemList(int start, int size, String searchFor, String searchContent);
}
