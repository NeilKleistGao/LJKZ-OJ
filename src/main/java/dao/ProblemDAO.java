package dao;

import entity.Problem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ProblemDAO implements AutoCloseable {
    private EntityManagerFactory factory;
    private EntityManager manager;

    public ProblemDAO() {
        this.factory = Persistence.createEntityManagerFactory("problems");
        this.manager = this.factory.createEntityManager();
    }

    public int getTotal() {
        List<String> list = this.manager.createQuery("select p.pid from Problem p").getResultList();
        return list.size();
    }

    public void insert(Problem problem) {
        this.manager.getTransaction().begin();
        this.manager.persist(problem);
        this.manager.getTransaction().commit();
    }

    public Problem getProblem(String pid) {
        return this.manager.find(Problem.class, pid);
    }

    public void remove(String pid) {
        this.manager.getTransaction().begin();
        Problem problem = getProblem(pid);
        this.manager.remove(problem);
        this.manager.getTransaction().commit();
    }

    public void update(Problem problem) {
        Problem old = getProblem(problem.getPid());
        old.setLabels(problem.getLabels());
        old.setProblemDesc(problem.getProblemDesc());
        old.setExampleOutput(problem.getExampleOutput());
        old.setExampleInput(problem.getExampleInput());
        old.setDataPath(problem.getDataPath());
        old.setCompetitionOnly(problem.isCompetitionOnly());
        old.setAcSubmit(problem.getAcSubmit());
        old.setTitle(problem.getTitle());
        old.setTotalSubmit(problem.getTotalSubmit());
        old.setTimeLimit(problem.getTimeLimit());
        old.setMemoryLimit(problem.getMemoryLimit());

        this.manager.getTransaction().begin();
        this.manager.persist(old);
        this.manager.getTransaction().commit();
    }

    public List<Problem> getProblems(int size, int start, String searchFor, String searchContent) {
        String sql = "select * from problems where not competitionOnly";
        if (!"".equals(searchContent) && searchContent != null) {
            sql += " and " + searchFor + " like '%" + searchContent + "%'";
        }

        sql += " limit " + start + ", " + size;
        Query query = this.manager.createNativeQuery(sql, Problem.class);
        List<Problem> list = query.getResultList();
        return list;
    }

    @Override
    public void close() throws Exception {
        if (this.manager != null) {
            this.manager.close();
        }
        this.factory.close();
    }
}
