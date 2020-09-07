package bean;

import utils.Entry;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@ManagedBean
@RequestScoped
public class CreateComBean {
//    private static int NUM=6;
    private String cid="";
    private String title="";
    private Date startDate;
    private Date overDate;
    private Entry[] createEntries= null;

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

    public void save(){

    }

    public void load(){

    }
}
