package bean;

import dao.ProblemDAO;
import dao.SubmissionDAO;
import entity.Problem;
import entity.Submission;
import utils.Judger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@ManagedBean
@RequestScoped
public class ProblemBean {
    private Problem problem = new Problem();
    private String code = "";

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void init() {
        try (ProblemDAO dao = new ProblemDAO()) {
            this.problem = dao.getProblem(this.problem.getPid());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

        Judger judger = Judger.getInstance();
        Submission submission = new Submission();
        submission.setEmail(new String(Base64.getDecoder().decode(map.get("uid").toString())));
        submission.setPid(problem.getPid());
        submission.setState("wait");
        submission.setSubmitCode(this.code);
        submission.setSubmitTime(new Date());
        submission.setNormalSubmit(!problem.isCompetitionOnly());

        judger.commit(submission);

        try (ProblemDAO dao = new ProblemDAO()) {
            this.problem.setTotalSubmit(this.problem.getTotalSubmit() + 1);
            dao.update(this.problem);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }
}
