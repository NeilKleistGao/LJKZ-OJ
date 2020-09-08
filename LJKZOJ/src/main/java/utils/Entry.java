package utils;

public class Entry {
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
