package utils;

import java.util.Date;

public class CompEntry{
    private String title;
    private String cid;
    private Date startT;
    private Date overT;
    private String name;


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

    public Date getStartT() {
        return startT;
    }

    public void setStartT(Date startT) {
        this.startT = startT;
    }

    public Date getOverT() {
        return overT;
    }

    public void setOverT(Date overT) {
        this.overT = overT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
