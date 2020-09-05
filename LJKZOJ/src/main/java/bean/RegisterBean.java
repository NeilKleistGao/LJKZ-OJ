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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean
@RequestScoped
public class RegisterBean {

    private final String EMAIL_PATTERN = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private String email, username, password, confirm;
    private String emailFeedback, usernameFeedback, passwordFeedback, confirmFeedback;
    @EJB
    private IUserDAO userDAO;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmFeedback() {
        return confirmFeedback;
    }

    public String getEmailFeedback() {
        return emailFeedback;
    }

    public String getPasswordFeedback() {
        return passwordFeedback;
    }

    public String getUsernameFeedback() {
        return usernameFeedback;
    }

    public void validateEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile(this.EMAIL_PATTERN);
        String email = value.toString().trim();
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            this.emailFeedback = "wrong email format!";
        }
        else {
            this.emailFeedback = "";
        }

        User user = userDAO.getUser(email);
        if (user != null) {
            this.emailFeedback = "this email has been used!";
        }
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
            if ("password".equals(com.getId())) {
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

    public String register() {
        if ("".equals(this.confirmFeedback) &&
                "".equals(this.emailFeedback) &&
                "".equals(this.passwordFeedback) &&
                "".equals(this.usernameFeedback)) {
            User user = new User();
            user.setEmail(this.email);
            user.setUsername(this.username);
            user.setPassword(MD5.encrypt(this.password));
            userDAO.addUser(user);

            return "success";
        }

        return "fail";
    }

    public String redirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        Map map = ex.getSessionMap();

        if (map.containsKey("uid")) {
            return "index";
        }

        return "";
    }
}
