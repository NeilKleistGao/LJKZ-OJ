package bean;

import com.sun.imageio.spi.RAFImageInputStreamSpi;
import dao.ICompetitionDAO;
import dao.IProblemDAO;
import dao.IRankDAO;
import entity.Competition;
import entity.Rank;
import utils.CompEntry;
import utils.Entry;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class CompetitionBean implements Serializable {
    private static final long serialVersionUID = 1145141919;
    private Competition competition = null;

    private Entry[] entries = null;
    private int userAC;
    private int userPenalty;
    private int rank;
    private String cid;

    private Date now;
    long progressNow;

    @EJB
    private ICompetitionDAO competitionDAO;
    @EJB
    private IProblemDAO problemDAO;
    @EJB
    private IRankDAO rankDAO;

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
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

    public long getProgress(){
        now = new Date();
        long startTimeS = competition.getBeginTime().getTime();
        long overTimeS = competition.getEndTime().getTime();
        long nowS = now.getTime();
        long totalS = overTimeS - startTimeS;
        long usedTimeS = nowS - startTimeS;
        progressNow = (usedTimeS / totalS) * 100;
        return progressNow;
    }

    public void init() {
        competition = competitionDAO.getCompetition(cid);
        List<HashMap<String, String>> list = problemDAO.getProblemListInCompetition(competition.getCid());

        this.entries = new Entry[list.size()];
        for (int i = 0; i < list.size(); i++) {
            this.entries[i] = new Entry();
            HashMap<String, String> map = list.get(i);
            this.entries[i].setPid(map.get("pid"));
            this.entries[i].setTitle(map.get("title"));
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        Rank rank = new Rank();
        rank.setCid(this.cid);
        rank.setEmail(new String(Base64.getDecoder().decode(map.get("uid").toString())));
        rank = rankDAO.getRank(rank);

        this.userAC = rank.getAc();
        this.userPenalty = rank.getPenalty();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
