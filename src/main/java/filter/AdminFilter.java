package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    private final static String ADMIN_EMAIL = "MTIwODM2ODU3N0BxcS5jb20=";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession map = httpRequest.getSession(false);

        if (map.getAttribute("uid") == null ||
        !map.getAttribute("uid").toString().equals(ADMIN_EMAIL)) {
            httpResponse.sendRedirect("/LJKZOJ-1.0-SNAPSHOT/index.xhtml");
        }

        chain.doFilter(request, response);
    }
}
