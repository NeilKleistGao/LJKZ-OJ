package bean;

import dao.IUserDAO;
import dao.UserDAO;
import entity.User;
import sun.security.validator.ValidatorException;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;
import java.io.File;

@ManagedBean
@RequestScoped
public class UserBean {
    private String uid = "";
    private boolean pass;
    private String oldPassword, newPassword, confirm;
    private String username, avatar = "default.png";
    private String email;
    private String usernameFeedback, passwordFeedback, confirmFeedback;
    private int acNum, waNum, tleNum, mleNum, ceNum, reNum;
    private Part file = null;

    @EJB
    private IUserDAO userDAO;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPass() {
        if (pass) {
            return "display: block";
        }
        else {
            return "display: none";
        }
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getConfirm() {
        return confirm;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getAvatar() {
        return "/resources/img/" + avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsernameFeedback() {
        return usernameFeedback;
    }

    public String getPasswordFeedback() {
        return passwordFeedback;
    }

    public String getConfirmFeedback() {
        return confirmFeedback;
    }

    public float getWaNum() {
        if (getSum() == 0) {
            return 0.0f;
        }
        return (float)waNum / (float)getSum() * 100.0f;
    }

    public float getTleNum() {
        if (getSum() == 0) {
            return 0.0f;
        }
        return  (float)tleNum / (float)getSum() * 100.0f;
    }

    public float getReNum() {
        if (getSum() == 0) {
            return 0.0f;
        }
        return (float)reNum / (float)getSum() * 100.0f;
    }

    public float getMleNum() {
        if (getSum() == 0) {
            return 0.0f;
        }
        return (float)mleNum / (float)getSum() * 100.0f;
    }

    public float getCeNum() {
        if (getSum() == 0) {
            return 0.0f;
        }
        return (float)ceNum / (float)getSum() * 100.0f;
    }

    public float getAcNum() {
        if (getSum() == 0) {
            return 0.0f;
        }
        return (float)acNum / (float)getSum() * 100.0f;
    }

    public int getSum() {
        return acNum + waNum + tleNum + mleNum + ceNum + reNum;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void checkSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        if (map.containsKey("uid") && "".equals(this.uid)) {
            this.uid = map.get("uid").toString();
        }

        this.pass = map.containsKey("uid") && map.get("uid").equals(this.uid);
        this.init();
    }

    private void init() {
        this.email = new String(Base64.getDecoder().decode(this.uid));
        User user = userDAO.getUser(this.email);
        if (user == null) {
            return;
        }

        this.username = user.getUsername();
        this.acNum = user.getAcNum();
        this.waNum = user.getWaNum();
        this.tleNum = user.getTleNum();
        this.mleNum = user.getMleNum();
        this.ceNum = user.getCeNum();
        this.reNum = user.getReNum();
    }

    public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value.toString().length() == 0) {
            this.usernameFeedback = "username can't be null!";
        }
        else if (value.toString().length() > 64) {
            this.usernameFeedback = "username is too long!";
        }
        else {
            this.usernameFeedback = "";
        }
    }

    public void validatePassword(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value.toString().length() < 6) {
            this.passwordFeedback = "password is too short!";
        }
        else if (value.toString().length() > 64) {
            this.passwordFeedback = "password is too long!";
        }
        else {
            this.passwordFeedback = "";
        }
    }

    public void validateConfirm(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIComponent password_ui = null;
        for (UIComponent com : component.getParent().getChildren()) {
            if ("new-password".equals(com.getId())) {
                password_ui = com;
                break;
            }
        }

        String pwd = ((HtmlInputSecret)password_ui).getValue().toString();
        if (!value.toString().equals(pwd)) {
            this.confirmFeedback = "two passwords are different!";
        }
        else {
            this.confirmFeedback = "";
        }
    }

    public void saveBasicInfo() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        if (map.containsKey("uid") && map.get("uid").equals(this.uid)) {
            if (file != null) {
                try (InputStream stream = file.getInputStream()) {
                    File file = new File("../applications/LJKZOJ-1.0-SNAPSHOT/resources/img", this.uid);
                    if (file.exists()) {
                        file.delete();
                        file = new File("../applications/LJKZOJ-1.0-SNAPSHOT/resources/img", this.uid);
                    }
                    Files.copy(stream, file.toPath());
                    this.avatar = this.uid;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.email = new String(Base64.getDecoder().decode(this.uid));
            User user = userDAO.getUser(this.email);
            user.setUsername(this.username);
            userDAO.updateUser(user);
        }
    }

    public void savePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        if (map.containsKey("uid") && map.get("uid").equals(this.uid)) {
            this.email = new String(Base64.getDecoder().decode(this.uid));
            User user = userDAO.getUser(this.email);
            if (user.getPassword().equals(MD5.encrypt(this.oldPassword))) {
                user.setPassword(MD5.encrypt(this.newPassword));
                userDAO.updateUser(user);
            }
        }
    }
}
