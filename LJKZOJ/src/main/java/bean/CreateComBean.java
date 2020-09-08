package bean;

import dao.ICompetitionDAO;
import dao.IProblemDAO;
import entity.Competition;
import entity.Problem;
import utils.Entry;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.nio.file.Files;
import java.io.File;
import java.util.List;

@ManagedBean
@RequestScoped
public class CreateComBean {
//    private static int NUM=6;
    private String cid="";
    private String title="";
    private Date startDate;
    private Date overDate;
    /*entry for all problems can be used in competition*/
    private Entry[] createEntries= null;
    /*entry for the problems already be used in the competition*/
    private Entry[] includedEntries = null;
    /*array to store the pid chosen*/
    private String[] checkedPid;

    @EJB
    private ICompetitionDAO competitionDAO;
    @EJB
    private IProblemDAO problemDAO;


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public Entry[] getCreateEntries() {
        return createEntries;
    }

    public void setCreateEntries(Entry[] createEntries) {
        this.createEntries = createEntries;
    }

    public Entry[] getIncludedEntries() {
        return includedEntries;
    }

    public void setIncludedEntries(Entry[] includedEntries) {
        this.includedEntries = includedEntries;
    }

    public String[] getCheckedPid() {
        return checkedPid;
    }

    public void setCheckedPid(String[] checkedPid) {
        this.checkedPid = checkedPid;
    }

    public void initCreateEntries(){
        List<Problem> problems = problemDAO.getProblemListInCompetition(this.cid);
        for(int i = 0; i < problems.size(); i++){
            this.createEntries[i] = new Entry();
            Problem problem = problems.get(i);
            this.createEntries[i].setPid(problem.getPid());
            this.createEntries[i].setLabels(problem.getLabels());
            this.createEntries[i].setTitle(problem.getTitle());
        }
    }

    public String save(){
        Competition competition = new Competition();
        String cid = MD5.encrypt(this.title + this.startDate + this.overDate);

        competition.setCid(cid);
        competition.setBeginTime(this.startDate);
        competition.setEndTime(this.overDate);
        competition.setTitle(this.title);

        for (Entry createEntry : createEntries) {
            for (String s : checkedPid) {
                if (createEntry.getPid().equals(s)) {
//                    this.includedEntries[j].setPid(createEntry.getPid());
//                    this.includedEntries[j].setLabels(createEntry.getLabels());
//                    this.includedEntries[j].setTitle(createEntry.getTitle());
                    competitionDAO.addCompetitionProblem(createEntry.getPid(), cid);
                }
            }
        }

        return "competitions";
    }

}
