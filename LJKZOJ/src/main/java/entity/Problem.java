package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "problems")
public class Problem implements Serializable {
    @Id
    private String pid;
    private String title, problemDesc, exampleInput, exampleOutput, labels;
    private int totalSubmit = 0, acSubmit = 0;
    private boolean competitionOnly;
    private int memoryLimit = 128, timeLimit = 1;

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalSubmit() {
        return totalSubmit;
    }

    public int getAcSubmit() {
        return acSubmit;
    }

    public String getExampleInput() {
        return exampleInput;
    }

    public String getExampleOutput() {
        return exampleOutput;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setAcSubmit(int acceptSubmit) {
        this.acSubmit = acceptSubmit;
    }

    public void setExampleInput(String exampleInput) {
        this.exampleInput = exampleInput;
    }

    public void setExampleOutput(String exampleOutput) {
        this.exampleOutput = exampleOutput;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public void setTotalSubmit(int totalSubmit) {
        this.totalSubmit = totalSubmit;
    }

    public void setCompetitionOnly(boolean competitionOnly) {
        this.competitionOnly = competitionOnly;
    }

    public boolean isCompetitionOnly() {
        return competitionOnly;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}
