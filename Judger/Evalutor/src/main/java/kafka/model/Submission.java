package com.wlm.kafka.example.model;

public class Submission {
    public int lid;//language id 1-c,2-cpp
    public int pid;//program id
    public String resources;// c or c++ resources
    public String execName;
    public String resFileName;
    public int uid;// user id
    public String timeLim;
    public String memoLim;

    @Override
    public String toString() {
        return "Submission{" +
                "lid=" + lid +
                ", pid=" + pid +
                ", resources='" + resources + '\'' +
                ", execName='" + execName + '\'' +
                ", resFileName='" + resFileName + '\'' +
                ", uid=" + uid +
                ", timeLim='" + timeLim + '\'' +
                ", memoLim='" + memoLim + '\'' +
                '}';
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getExecName() {
        return execName;
    }

    public void setExecName(String execName) {
        this.execName = execName;
    }

    public String getResFileName() {
        return resFileName;
    }

    public void setResFileName(String resFileName) {
        this.resFileName = resFileName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTimeLim() {
        return timeLim;
    }

    public void setTimeLim(String timeLim) {
        this.timeLim = timeLim;
    }

    public String getMemoLim() {
        return memoLim;
    }

    public void setMemoLim(String memoLim) {
        this.memoLim = memoLim;
    }
}