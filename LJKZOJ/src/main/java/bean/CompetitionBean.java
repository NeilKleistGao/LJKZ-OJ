package bean;

import dao.IProblemDAO;
import entity.Competition;
import utils.CompEntry;
import utils.Entry;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@ManagedBean
@RequestScoped
public class CompetitionBean {

    private Competition competition = new Competition();

    private int NUMBER_OF_PROBLEMS;
    private Entry[] entries = new Entry[NUMBER_OF_PROBLEMS];
    private int userAC;
    private int userPenalty;
    private int rank;

    private Date now;
    long progressNow;



    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public int getNUMBER_OF_PROBLEMS() {
        return NUMBER_OF_PROBLEMS;
    }

    public void setNUMBER_OF_PROBLEMS(int NUMBER_OF_PROBLEMS) {
        this.NUMBER_OF_PROBLEMS = NUMBER_OF_PROBLEMS;
    }

    public Entry[] getEntries() {
        return entries;
    }

    public void setEntries(Entry[] entries) {
        this.entries = entries;
    }

    public int getUserAC() {
        return userAC;
    }

    public void setUserAC(int userAC) {
        this.userAC = userAC;
    }

    public int getUserPenalty() {
        return userPenalty;
    }

    public void setUserPenalty(int userPenalty) {
        this.userPenalty = userPenalty;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public long progress(){
        long startTimeS = competition.getBeginTime().getTime();
        long overTimeS = competition.getEndTime().getTime();
        long nowS = now.getTime();
        long totalS = overTimeS - startTimeS;
        long usedTimeS = nowS - startTimeS;
        progressNow = (usedTimeS / totalS) * 100;
        return progressNow;
    }
}
