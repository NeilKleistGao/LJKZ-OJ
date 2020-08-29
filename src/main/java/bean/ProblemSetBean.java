package bean;

import dao.ProblemDAO;
import dao.SubmissionDAO;
import entity.Problem;
import utils.Pagination;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@ManagedBean
@RequestScoped
public class ProblemSetBean {
    private final static int NUMBER_OF_ENTRIES_IN_PAGE = 16;
    public static class Entry {
        private String title;
        private String pid;
        private String labels;
        private boolean accepted;
        private int acSubmit, totalSubmit;

        public String getPid() {
            return pid;
        }

        public String getTableClass() {
            if (accepted) {
                return "table-primary";
            }
            return "table-active";
        }

        public boolean isAccepted() {
            return accepted;
        }

        public String getTitle() {
            return title;
        }

        public String getLabels() {
            return labels;
        }

        public String getAcPercent() {
            if (this.totalSubmit == 0) {
                return "0%";
            }

            float val = (float)this.acSubmit / (float)this.totalSubmit * 100.f;
            return val + "%";
        }

        public void setAcSubmit(int acSubmit) {
            this.acSubmit = acSubmit;
        }

        public int getTotalSubmit() {
            return totalSubmit;
        }

        public void setAccepted(boolean accepted) {
            this.accepted = accepted;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public void setTotalSubmit(int totalSubmit) {
            this.totalSubmit = totalSubmit;
        }
    }

    private int pageNumber;
    private int totalNumber;
    private Entry[] entries = new Entry[NUMBER_OF_ENTRIES_IN_PAGE];
    private String searchFor = "title", searchContent = "";
    private Pagination[] paginations = null;

    public Entry[] getEntries() {
        return entries;
    }

    public Pagination[] getPaginations() {
        return paginations;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setEntries(Entry[] entries) {
        this.entries = entries;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public void setPaginations(Pagination[] paginations) {
        this.paginations = paginations;
    }

    public void init() {
        try (ProblemDAO dao = new ProblemDAO()) {
            this.totalNumber = (dao.getTotal() + NUMBER_OF_ENTRIES_IN_PAGE - 1) / NUMBER_OF_ENTRIES_IN_PAGE;
            List<Problem> problems = dao.getProblems(NUMBER_OF_ENTRIES_IN_PAGE, this.pageNumber - 1, this.searchFor, this.searchContent);
            for (int i = 0; i < problems.size(); i++) {
                this.entries[i] = new Entry();
                Problem problem = problems.get(i);
                this.entries[i].setPid(problem.getPid());
                this.entries[i].setAcSubmit(problem.getAcSubmit());
                this.entries[i].setLabels(problem.getLabels());
                this.entries[i].setTitle(problem.getTitle());
            }

            this.setAccepted();
        }
        catch (Exception e) {
            e.printStackTrace();
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

        this.setupPaginations();
    }

    private void setupPaginations() {
        this.paginations[0].setNotation("<<");
        this.paginations[this.paginations.length - 1].setNotation(">>");
        if (this.pageNumber == 1) {
            this.paginations[0].setLink("#");
            this.paginations[0].setStyle("page-item disabled");
        }
        else {
            this.paginations[0].setLink("/problems.xhtml?page=" + String.valueOf(this.pageNumber - 1));
            this.paginations[0].setStyle("page-item");
        }

        if (this.pageNumber == this.totalNumber) {
            this.paginations[this.paginations.length - 1].setLink("#");
            this.paginations[this.paginations.length - 1].setStyle("page-item disabled");
        }
        else {
            this.paginations[this.paginations.length - 1].setLink("/problems.xhtml?page=" + String.valueOf(this.pageNumber + 1));
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
                this.paginations[i].setLink("/problems.xhtml?page=" + String.valueOf(j));
                this.paginations[i].setStyle("page-item");
            }
        }
    }

    private void setAccepted() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        if (!map.containsKey("uid")) {
            return;
        }

        try (SubmissionDAO dao = new SubmissionDAO()) {
            for (int i = 0; i < NUMBER_OF_ENTRIES_IN_PAGE; i++) {
                this.entries[i].setAccepted(
                        dao.isAccepted(new String(Base64.getDecoder().decode(map.get("uid").toString())), this.entries[i].getPid()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void search() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();

        try {
            ex.redirect(
                    "/LJKZOJ-1.0-SNAPSHOT/problems.xhtml?page=1&for="
                            + this.searchFor + "&content=" + this.searchContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String create() {
        return "create";
    }
}
