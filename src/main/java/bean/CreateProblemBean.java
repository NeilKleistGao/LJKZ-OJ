package bean;

import dao.ProblemDAO;
import entity.Problem;
import utils.MD5;
import utils.Zip;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.io.File;

@ManagedBean
@RequestScoped
public class CreateProblemBean {
    private String pid = "";
    private String title = "";
    private String problemDesc = "";
    private String labels = "";
    private String exampleInput = "", exampleOutput = "", dataPath;
    private boolean competitionOnly = false;
    private boolean update = false;
    private Part file = null;
    private int timeLimit = 1, memoryLimit = 128;

    @EJB
    private ProblemDAO problemDAO;

    public boolean isCompetitionOnly() {
        return competitionOnly;
    }

    public void setCompetitionOnly(boolean competitionOnly) {
        this.competitionOnly = competitionOnly;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public void setExampleOutput(String exampleOutput) {
        this.exampleOutput = exampleOutput;
    }

    public void setExampleInput(String exampleInput) {
        this.exampleInput = exampleInput;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public String getExampleOutput() {
        return exampleOutput;
    }

    public String getExampleInput() {
        return exampleInput;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getLabels() {
        return labels;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public String getPid() {
        return pid;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile() {
        return file;
    }

    public void load() {
        if ("".equals(this.pid)) {
            Problem problem = problemDAO.getProblem(pid);
            this.problemDesc = problem.getProblemDesc();
            this.competitionOnly = problem.isCompetitionOnly();
            this.exampleInput = problem.getExampleInput();
            this.exampleOutput = problem.getExampleOutput();
            this.labels = problem.getLabels();
            this.title = problem.getTitle();
            this.timeLimit = problem.getTimeLimit();
            this.memoryLimit = problem.getMemoryLimit();
            this.update = true;
        }
    }

    public String save() {
        Problem problem = new Problem();
        String pid = MD5.encrypt(this.title + this.problemDesc);
        if (this.update) {
            pid = this.pid;
        }
        problem.setPid(pid);
        problem.setCompetitionOnly(this.competitionOnly);

        if (this.file != null) {
            try (InputStream stream = this.file.getInputStream()) {
                File dataFile = new File("../data", pid);
                if (dataFile.exists()) {
                    dataFile.delete();
                    dataFile = new File("../data", pid);
                }

                Files.copy(stream, dataFile.toPath());
                Zip.unzip(pid);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        problem.setExampleInput(this.exampleInput);
        problem.setExampleOutput(this.exampleOutput);
        problem.setProblemDesc(this.problemDesc);
        problem.setTitle(this.title);
        problem.setLabels(this.labels);
        problem.setMemoryLimit(this.memoryLimit);
        problem.setTimeLimit(this.timeLimit);

        if (this.update){
            problemDAO.addProblem(problem);
        }
        else {
            problemDAO.updateProblem(problem);
        }

        return "problems";
    }
}
