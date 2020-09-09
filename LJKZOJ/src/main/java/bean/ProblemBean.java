package bean;

import dao.IProblemDAO;
import dao.ISubmissionDAO;
import entity.Problem;
import entity.Submission;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
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

    @EJB
    private IProblemDAO problemDAO;
    @EJB
    private ISubmissionDAO submissionDAO;

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

        Submission submission = new Submission();
        submission.setEmail(new String(Base64.getDecoder().decode(map.get("uid").toString())));
        submission.setPid(problem.getPid());
        submission.setState("wait");
        submission.setSubmitTime(new Date());
        submission.setNormalSubmit(!problem.isCompetitionOnly());
        submissionDAO.insert(submission);

        problemDAO.updateProblem(this.problem);

        return "success";
    }
}
