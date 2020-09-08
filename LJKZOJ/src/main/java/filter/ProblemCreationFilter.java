package filter;

import dao.IUserDAO;
import entity.User;
import utils.PermissionChecker;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

public class ProblemCreationFilter implements Filter {
    @EJB
    private IUserDAO userDAO;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession map = httpRequest.getSession(false);

        if (map.getAttribute("uid") == null) {
            String email = new String(Base64.getDecoder().decode((String)map.getAttribute("uid")));
            User user = userDAO.getUser(email);

            if (user != null &&
                    PermissionChecker.getInstance().check(user.getPermissions(), PermissionChecker.PROBLEM_PERMISSION)) {
                httpResponse.sendRedirect("/LJKZOJ-1.0-SNAPSHOT/problems.xhtml");
            }
        }

        chain.doFilter(request, response);
    }
}
