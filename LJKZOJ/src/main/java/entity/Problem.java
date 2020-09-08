package entity;

import java.io.Serializable;

public class Problem implements Serializable {
    private String pid;
    private String title, problemDesc, exampleInput, exampleOutput, labels;
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

    public String getExampleInput() {
        return exampleInput;
    }

    public String getExampleOutput() {
        return exampleOutput;
    }

    public String getProblemDesc() {
        return problemDesc;
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
