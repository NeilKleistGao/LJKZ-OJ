package entity;

import java.io.Serializable;
import java.util.Date;

public class Submission implements Serializable {
    private String email;
    private String pid;
    private Date submitTime;

    private String state, submitCode;
    private boolean normalSubmit;
    private int timeUsed, memoryUsed;
    private String info;

    public boolean isNormalSubmit() {
        return normalSubmit;
    }

    public void setNormalSubmit(boolean normalSubmit) {
        this.normalSubmit = normalSubmit;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public String getSubmitCode() {
        return submitCode;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSubmitCode(String submitCode) {
        this.submitCode = submitCode;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setMemoryUsed(int memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }
}
