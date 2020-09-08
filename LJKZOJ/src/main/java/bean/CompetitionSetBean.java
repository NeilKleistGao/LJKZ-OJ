package bean;



import dao.CompetitionDAO;
import dao.ICompetitionDAO;
import entity.Competition;
import entity.Problem;
import utils.Entry;
import utils.Pagination;
import utils.CompEntry;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class CompetitionSetBean {
    private final static int NUMBER_OF_ENTRIES_IN_PAGE = 16;

    private int pageNumber;
    private int totalNumber;
    private CompEntry[] compEntries = new CompEntry[NUMBER_OF_ENTRIES_IN_PAGE];
    private String searchFor="Finished";
    private Pagination[] paginations = null;

<<<<<<< HEAD
    @EJB
    private CompetitionDAO competitionDAO;

=======
>>>>>>> f9aaf3417dcc0c9e2d32b5706b5e6e4428535372
    public CompEntry[] getCompEntries() {
        return compEntries;
    }

    public void setCompEntries(CompEntry[] compEntries) {
        this.compEntries = compEntries;
    }

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

    public String getSearchFor() {
        return searchFor;
    }

<<<<<<< HEAD
    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
=======
    public void setSearchFor(String searchfor) {
        this.searchFor = searchfor;
>>>>>>> f9aaf3417dcc0c9e2d32b5706b5e6e4428535372
    }

    public Pagination[] getPaginations() {
        return paginations;
    }

    public void setPaginations(Pagination[] paginations) {
        this.paginations = paginations;
    }

<<<<<<< HEAD
    private void setupPaginations(){
=======
    private void setupPagination(){
>>>>>>> f9aaf3417dcc0c9e2d32b5706b5e6e4428535372
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

    public void init(){
        this.totalNumber = (competitionDAO.getComTotal() + NUMBER_OF_ENTRIES_IN_PAGE - 1) / NUMBER_OF_ENTRIES_IN_PAGE;
        List<Competition> competitions = competitionDAO.getCompetitionList(this.pageNumber, NUMBER_OF_ENTRIES_IN_PAGE,
                searchFor);
        for (int i = 0; i < competitions.size(); i++) {
            this.compEntries[i] = new CompEntry();
            Competition competition = competitions.get(i);
            this.compEntries[i].setCid(competition.getCid());
            this.compEntries[i].setTitle(competition.getTitle());
            this.compEntries[i].setStartT(competition.getBeginTime());
            this.compEntries[i].setOverT(competition.getEndTime());
            this.compEntries[i].setCreator(competition.getCreator());
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

    public void CompStateChange(ValueChangeEvent e){
        searchFor = e.getNewValue().toString();

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();

        try {
            ex.redirect(
                    "/LJKZOJ-1.0-SNAPSHOT/problems.xhtml?page=1&for="
                            + searchFor);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
