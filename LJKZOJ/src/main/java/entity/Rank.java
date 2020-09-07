package entity;

import java.io.Serializable;

public class Rank implements Serializable {
    private String cid;
    private String email;
    private int ac, penalty;
    private int rank;
    private float percent;

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRank() {
        return rank;
    }

    public int getAc() {
        return ac;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
