package bean;

import dao.UserDAO;
import entity.User;
import sun.security.validator.ValidatorException;
import utils.MD5;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Base64;
import java.util.Map;

@ManagedBean
@RequestScoped
public class LoginBean {
    private String email, password, error;
    @EJB
    private UserDAO userDAO;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getError() {
        return error;
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = value.toString();
        UIComponent email_ui = null;

        for (UIComponent com : component.getParent().getChildren()) {
            if ("email".equals(com.getId())) {
                email_ui = com;
                break;
            }
        }

        String email = ((HtmlInputText)email_ui).getValue().toString();
        if ("".equals(email) || "".equals(password)) {
            this.error = "blank field is not allowed!";
        }

        User user = userDAO.getUser(email);
        if (user.getPassword().equals(MD5.encrypt(password))) {
            ExternalContext ex = context.getExternalContext();
            Map session_map = ex.getSessionMap();
            session_map.put("uid", Base64.getEncoder().encodeToString(email.getBytes()));
            session_map.put("username", user.getUsername());
            this.error = "";
        }
        else {
            this.error = "wrong username or password!";
        }
    }

    public String login() {
        if ("".equals(this.error)) {
            return "success";
        }
        return "";
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
