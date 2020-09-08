package bean;

import dao.ICompetitionDAO;
import dao.IProblemDAO;
import entity.Competition;
import utils.Entry;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.nio.file.Files;
import java.io.File;

@ManagedBean
@RequestScoped
public class CreateComBean {
//    private static int NUM=6;
    private String cid="";
    private String title="";
    private Date startDate;
    private Date overDate;
    private Entry[] createEntries= null;

    @EJB
    private ICompetitionDAO competitionDAO;

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

    public String save(){
        Competition competition = new Competition();
        String cid = MD5.encrypt(this.title + this.startDate + this.overDate);

        competition.setCid(cid);
        competition.setBeginTime(this.startDate);
        competition.setEndTime(this.overDate);
        competition.setTitle(this.title);

        return "competitions";
    }

}
