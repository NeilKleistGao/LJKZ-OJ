package bean;

import utils.Entry;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class CompetitionBean {

    private int NUMBER_OF_PROBLEMS;
    private Entry[] entries = new Entry[NUMBER_OF_PROBLEMS];
    private int userAC;
    private int userPenalty;
    private int rank;

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
}
