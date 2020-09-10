package bean;

import dao.IProblemDAO;
import entity.Problem;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;

@ManagedBean
@ViewScoped
public class CreateProblemBean implements Serializable {
    private String pid = "";
    private String title = "";
    private String problemDesc = "";
    private String labels = "";
    private String exampleInput = "", exampleOutput = "";
    private boolean competitionOnly = false;
    private boolean update = false;
    private Part file = null;
    private int timeLimit = 1000, memoryLimit = 65536;

    @EJB
    private IProblemDAO problemDAO;

    public boolean isCompetitionOnly() {
        return competitionOnly;
    }

    public void setCompetitionOnly(boolean competitionOnly) {
        this.competitionOnly = competitionOnly;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public void setExampleOutput(String exampleOutput) {
        this.exampleOutput = exampleOutput;
    }

    public void setExampleInput(String exampleInput) {
        this.exampleInput = exampleInput;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public String getExampleOutput() {
        return exampleOutput;
    }

    public String getExampleInput() {
        return exampleInput;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getLabels() {
        return labels;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public String getPid() {
        return pid;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile() {
        return file;
    }

    public void load() {
        if (!"".equals(this.pid)) {
            Problem problem = problemDAO.getProblem(pid);
            this.problemDesc = problem.getProblemDesc();
            this.competitionOnly = problem.isCompetitionOnly();
            this.exampleInput = problem.getExampleInput();
            this.exampleOutput = problem.getExampleOutput();
            this.labels = problem.getLabels();
            this.title = problem.getTitle();
            this.timeLimit = problem.getTimeLimit();
            this.memoryLimit = problem.getMemoryLimit();
            this.update = true;
        }
    }

    public String save() {
        Problem problem = new Problem();
        String pid = MD5.encrypt(this.title + this.problemDesc);
        if (this.update) {
            pid = this.pid;
        }
        problem.setPid(pid);
        problem.setCompetitionOnly(this.competitionOnly);

        if (this.file != null) {
            try (InputStream stream = file.getInputStream()) {
                File file = new File("../temp", pid + ".zip");
                if (file.exists()) {
                    file.delete();
                }
                file = new File("../" + pid + ".zip");
                OutputStream outputStream = new FileOutputStream(file);

                byte[] bytes = new byte[1024];
                while ((stream.read(bytes)) > 0) {
                    outputStream.write(bytes);
                }

                outputStream.close();

                HttpClient client = HttpClients.createDefault();
                HttpPost post = new HttpPost("http://localhost:8080/Rest-1.0-SNAPSHOT/upload?pid=" + pid);
                FileBody body = new FileBody(file);
                HttpEntity entity = MultipartEntityBuilder.create()
                        .addPart("file", body)
                        .build();

                post.setEntity(entity);
                client.execute(post);
                file.delete();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        problem.setExampleInput(this.exampleInput);
        problem.setExampleOutput(this.exampleOutput);
        problem.setProblemDesc(this.problemDesc);
        problem.setTitle(this.title);
        problem.setLabels(this.labels);
        problem.setMemoryLimit(this.memoryLimit);
        problem.setTimeLimit(this.timeLimit);

        if (this.update){
            problemDAO.updateProblem(problem);
        }
        else {
            problemDAO.addProblem(problem);
        }

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.redirect("/LJKZOJ-1.0-SNAPSHOT/problems.xhtml?page=1");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }
}
