package entity;

import java.io.Serializable;

public class Rank implements Serializable {
    private String cid;
    private String email;
    private int ac, penalty;
    private int rank;
    private Float percent;

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

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}
