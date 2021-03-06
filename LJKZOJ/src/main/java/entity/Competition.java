package entity;

import java.io.Serializable;
import java.util.Date;

public class Competition implements Serializable {
    private String cid;
    private Date beginTime, endTime;
    private String title;
    private String creator;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getCid() {
        return cid;
    }

    public String getCreator() {
        return creator;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
