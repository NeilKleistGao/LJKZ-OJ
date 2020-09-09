package com.wlm.kafka.example.model;

import java.util.Date;

public class Submission {

    private String email;
    private int pid;

    private Date submitTime;
    private String code;
    private int memoryLimit;
    private int timeLimit;

    @Override
    public String toString() {
        return "Submission{" +
                "email='" + email + '\'' +
                ", pid=" + pid +
                ", submitTime=" + submitTime +
                ", code='" + code + '\'' +
                ", memoryLimit=" + memoryLimit +
                ", timeLimit=" + timeLimit +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}