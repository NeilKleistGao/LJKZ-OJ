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
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;
import java.nio.file.Files;
import java.io.File;

@ManagedBean
@ViewScoped
public class CreateComBean implements Serializable {

    private static final long serialVersionUID = 1145141919;
//    private static int NUM=6;
    private String cid="";
    private String title="";
    private Date startDate;
    private Date overDate;
    /*entry for all problems can be used in competition*/
    private Entry[] createEntries= null;
    private String[] show = null;
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

    public String[] getCheckedPid() {
        return checkedPid;
    }

    public void setCheckedPid(String[] checkedPid) {
        this.checkedPid = checkedPid;
    }

    public void initCreateEntries(){
        List<Problem> problems = problemDAO.getAvailableProblemInCompetition();
        this.createEntries = new Entry[problems.size()];
        this.checkedPid = new String[problems.size()];
        this.show = new String[problems.size()];
        for(int i = 0; i < problems.size(); i++){
            this.createEntries[i] = new Entry();
            Problem problem = problems.get(i);
            this.createEntries[i].setPid(problem.getPid());
            this.createEntries[i].setLabels(problem.getLabels());
            this.createEntries[i].setTitle(problem.getTitle());
            this.show[i] = problem.getPid() + ": " + problem.getTitle();
        }
    }

    public String save(){
        Competition competition = new Competition();
        String cid = MD5.encrypt(this.title + this.startDate + this.overDate);

        competition.setCid(cid);
        competition.setBeginTime(this.startDate);
        competition.setEndTime(this.overDate);
        competition.setTitle(this.title);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map map = externalContext.getSessionMap();
        competition.setCreator(new String(Base64.getDecoder().decode(map.get("uid").toString())));

        competitionDAO.addCompetition(competition);

        for (Entry createEntry : createEntries) {
            for (String s : checkedPid) {
                if (s != null && s.equals(createEntry.getPid() + ": " + createEntry.getTitle())) {
                    competitionDAO.addCompetitionProblem(createEntry.getPid(), cid);
                }
            }
        }

        return "competitions";
    }

    public String[] getShow() {
        return show;
    }
}
