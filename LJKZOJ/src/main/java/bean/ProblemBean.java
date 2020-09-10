package bean;

import dao.IProblemDAO;
import dao.IRankDAO;
import dao.ISubmissionDAO;
import entity.Problem;
import entity.Rank;
import entity.Submission;
import myKafka.ISubmissionProducer;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.*;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@ManagedBean
@ViewScoped
public class ProblemBean implements Serializable {
    private Problem problem = new Problem();
    private String code = "";
    private String cid = "";

    @EJB
    private IProblemDAO problemDAO;
    @EJB
    private ISubmissionDAO submissionDAO;
    @EJB
    private ISubmissionProducer submissionProducer;
    @EJB
    private IRankDAO rankDAO;

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void init() {
        this.problem = problemDAO.getProblem(this.problem.getPid());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        if (!map.containsKey("uid")) {
            return "fail";
        }

        this.problem = problemDAO.getProblem(this.problem.getPid());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        Date now = calendar.getTime();
        String email = new String(Base64.getDecoder().decode(map.get("uid").toString()));
        Submission submission = new Submission();
        submission.setSid(MD5.encrypt(email + problem.getPid() + now));
        submission.setEmail(email);
        submission.setPid(problem.getPid());
        submission.setState("wait");
        submission.setSubmitTime(now);
        submission.setNormalSubmit(!problem.isCompetitionOnly());
        submissionDAO.insert(submission);

        myKafka.Submission submissionPro = new myKafka.Submission();

        submissionPro.setSid(submission.getSid());
        submissionPro.setEmail(email);
        submissionPro.setPid(problem.getPid());
        submissionPro.setSubmitTime(now);
        submissionPro.setCode(this.code);
        submissionPro.setMemoryLimit(problem.getMemoryLimit());
        submissionPro.setTimeLimit(problem.getTimeLimit());
        submissionPro.setCid(cid);

        submissionProducer.send("test", "jlmnb", submissionPro);

        if (submission.isNormalSubmit()) {
            try (OutputStream outputStream =
                         new FileOutputStream(new File("../code/" + MD5.encrypt(email + problem.getPid() + now)))) {
                outputStream.write(code.getBytes());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Rank rank = new Rank();
            rank.setEmail(email);
            rank.setCid(this.cid);
            rank = rankDAO.getRank(rank);

            if (rank == null) {
                rank = new Rank();
                rank.setEmail(email);
                rank.setCid(this.cid);
                rank.setAc(0);
                rank.setPenalty(0);
                rank.setPercent(null);
                rank.setRank(null);

                rankDAO.addRank(rank);
            }
        }

        return "success";
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }
}
