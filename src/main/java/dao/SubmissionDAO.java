package dao;

import entity.Submission;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class SubmissionDAO implements AutoCloseable {
    private EntityManagerFactory factory;
    private EntityManager manager;

    public SubmissionDAO() {
        this.factory = Persistence.createEntityManagerFactory("submissions");
        this.manager = this.factory.createEntityManager();
    }

    public boolean isAccepted(String email, String pid) {
        Query query = this.manager.createQuery(
                "select s.email from Submission s where s.email=:email and s.pid =:pid and s.state = 'ac'");
        query.setParameter("email", email);
        query.setParameter("pid", pid);
        List<String> list = query.getResultList();
        return !list.isEmpty();
    }

    public void insert(Submission submission) {
        this.manager.getTransaction().begin();
        this.manager.persist(submission);
        this.manager.getTransaction().commit();
    }

    public Submission getSubmission(String email, String pid, Date time) {
        Query query = this.manager.createQuery(
                "select s from Submission s where s.email=:email and s.pid =:pid and s.submitTime=:time",
                Submission.class);
        query.setParameter("email", email);
        query.setParameter("pid", pid);
        query.setParameter("time", time);
        List<Submission> list = query.getResultList();
        return list.get(0);
    }

    @Override
    public void close() throws Exception {
        if (this.manager != null) {
            this.manager.close();
        }

        this.factory.close();
    }
}
