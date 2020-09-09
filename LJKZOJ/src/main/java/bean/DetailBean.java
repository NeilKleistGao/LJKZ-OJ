package bean;

import dao.IProblemDAO;
import dao.ISubmissionDAO;
import entity.Problem;
import entity.Submission;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.entity.FileEntity;
import utils.SubmitEntry;

@ManagedBean
@RequestScoped

public class DetailBean {
    private String pid;
    private String codeid;
    private String email;
    private  Date submitTime;

    private String[] codeString;
    private String codeFile;

    private int timeUsed, memoryUsed;
    private String title;
    private String state;

    @EJB
    private ISubmissionDAO submissionDAO;
    @EJB
    private IProblemDAO problemDAO;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String[] getCodeString() {
        return codeString;
    }

    public void setCodeString(String[] codeString) {
        this.codeString = codeString;
    }

    public String getCodeFile() {
        return codeFile;
    }

    public void setCodeFile(String codeFile) {
        this.codeFile = codeFile;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(int memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void init() throws IOException {
        Submission submissionDetail = submissionDAO.getSubmission(email,pid,submitTime);
        Problem problemDetail = problemDAO.getProblem(pid);
        this.timeUsed = submissionDetail.getTimeUsed();
        this.memoryUsed = submissionDetail.getMemoryUsed();
        this.title = problemDetail.getTitle();
        this.state = submissionDetail.getState();
        this.codeFile= MD5.encrypt(submissionDetail.getEmail() + submissionDetail.getPid() + submissionDetail.getSubmitTime());
        getProblemCode();
        }

    public void getProblemCode() throws IOException{
        BufferedReader bfr = new BufferedReader(new FileReader(codeFile));
        String str = null;
        for(int lineNumber = 0 ;(str = bfr.readLine()) != null ;lineNumber++){
            this.codeString[lineNumber] = str;
        }
        bfr.close();
    }
}
