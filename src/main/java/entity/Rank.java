package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rank")
public class Rank {
    @Id
    private String cid;
    @Id
    private String email;
    private int ac, penalty;
    private float rank;

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

    public float getRank() {
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

    public void setRank(float rank) {
        this.rank = rank;
    }
}
