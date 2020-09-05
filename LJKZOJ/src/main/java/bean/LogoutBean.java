package bean;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.awt.event.ActionEvent;

@ManagedBean
@RequestScoped
public class LogoutBean {
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();
        HttpSession session = (HttpSession) ex.getSession(false);
        session.invalidate();

        return "index";
    }
}
