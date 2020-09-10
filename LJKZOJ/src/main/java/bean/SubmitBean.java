package bean;

import dao.IProblemDAO;
import dao.ISubmissionDAO;
import entity.Problem;
import entity.Submission;
import entity.User;
import utils.Pagination;
import utils.SubmitEntry;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ManagedBean
@RequestScoped

public class SubmitBean {
    private static final int NUMBER_OF_ENTRIES_IN_PAG = 16;
    private String title;
    private String name;
    private int memoryUsed;
    private int timeUsed;

    @EJB
    private ISubmissionDAO submissionDAO;
    private int pageNumber;
    private int totalNumber;
    private Pagination[] paginations = null;

    private SubmitEntry[] submitEntries = null;

    /*@EJB
    private Problem problems;
    @EJB
    private Submission submissions;
    @EJB
    private User users;*/

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Pagination[] getPaginations() {
        return paginations;
    }

    public void setPaginations(Pagination[] paginations) {
        this.paginations = paginations;
    }

    public SubmitEntry[] getSubmitEntries() {
        return submitEntries;
    }

    public void setSubmitEntries(SubmitEntry[] submitEntries) {
        this.submitEntries = submitEntries;
    }

    public void init(){
        this.totalNumber = (submissionDAO.getAllSubmitTotal() + NUMBER_OF_ENTRIES_IN_PAG - 1) / NUMBER_OF_ENTRIES_IN_PAG;
        List<HashMap<String, Object>> submit = submissionDAO.getSubmissionList(0,NUMBER_OF_ENTRIES_IN_PAG);
        this.submitEntries = new SubmitEntry[NUMBER_OF_ENTRIES_IN_PAG];
        for (int i = 0; i < submit.size(); i++){
            this.submitEntries[i] = new SubmitEntry();
            HashMap<String, Object> submitMap = submit.get(i);
            this.submitEntries[i].setEmail(submitMap.get("email").toString());
            this.submitEntries[i].setPid(submitMap.get("pid").toString());
            this.submitEntries[i].setSubmitTime((Date)submitMap.get("submitTime"));
            this.submitEntries[i].setState(submitMap.get("state").toString());
            this.submitEntries[i].setNormalSubmit((boolean)submitMap.get("normalSubmit"));
            this.submitEntries[i].setTimeUsed((Integer) submitMap.get("timeUsed"));
            this.submitEntries[i].setMemoryUsed((Integer) submitMap.get("memoryUsed"));
            this.submitEntries[i].setInfo(submitMap.get("info").toString());
            this.submitEntries[i].setTitle(submitMap.get("title").toString());
            this.submitEntries[i].setUsername(submitMap.get("username").toString());
            this.submitEntries[i].setSid(submitMap.get("sid").toString());
        }

        if (this.totalNumber == 0) {
            this.totalNumber = 1;
        }
        if (this.totalNumber < 5) {
            this.paginations = new Pagination[this.totalNumber + 2];
        }
        else {
            this.paginations = new Pagination[7];
        }
        for (int i = 0; i < this.paginations.length; i++) {
            this.paginations[i] = new Pagination();
        }

        this.setupPagination();
    }

    public void setupPagination() {
        if (this.pageNumber <= 0) {
            this.pageNumber = 1;
        }

        this.paginations[0].setNotation("<<");
        this.paginations[this.paginations.length - 1].setNotation(">>");
        if (this.pageNumber == 1) {
            this.paginations[0].setLink("#");
            this.paginations[0].setStyle("page-item disabled");
        }
        else {
            this.paginations[0].setLink("/submit.xhtml?page=" + String.valueOf(this.pageNumber - 1));
            this.paginations[0].setStyle("page-item");
        }

        if (this.pageNumber == this.totalNumber) {
            this.paginations[this.paginations.length - 1].setLink("#");
            this.paginations[this.paginations.length - 1].setStyle("page-item disabled");
        }
        else {
            this.paginations[this.paginations.length - 1].setLink("/submit.xhtml?page=" + String.valueOf(this.pageNumber + 1));
            this.paginations[this.paginations.length - 1].setStyle("page-item");
        }

        int start, end;
        if (this.totalNumber == 0) {
            start = 1; end = 1;
        }
        else if (this.totalNumber < 5) {
            start = 1; end = this.totalNumber;
        }
        else if (this.pageNumber < 3) {
            start = 1; end = 5;
        }
        else if (this.pageNumber + 2 > this.totalNumber) {
            start = this.totalNumber - 4; end = this.totalNumber;
        }
        else {
            start = this.pageNumber - 2; end = this.pageNumber + 2;
        }

        for (int i = 1, j = start; j <= end; i++, j++) {
            this.paginations[i].setNotation(String.valueOf(j));
            if (j == this.pageNumber) {
                this.paginations[i].setLink("#");
                this.paginations[i].setStyle("page-item activate");
            }
            else {
                this.paginations[i].setLink("/submit.xhtml?page=" + String.valueOf(j));
                this.paginations[i].setStyle("page-item");
            }
        }
    }


}
