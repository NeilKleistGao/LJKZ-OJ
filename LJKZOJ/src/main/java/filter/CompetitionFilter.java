package filter;

import dao.ICompetitionDAO;
import entity.Competition;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CompetitionFilter implements Filter {
    @EJB
    private ICompetitionDAO competitionDAO;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        String cid = httpRequest.getParameter("cid");
        if (cid == null) {
            cid = "";
        }
        Competition competition = competitionDAO.getCompetition(cid);

//        if (competition != null) {
//            Date now = new Date();
//            if (now.before(competition.getBeginTime())) {
//                httpResponse.sendRedirect("/LJKZOJ-1.0-SNAPSHOT/competitions.xhtml");
//            }
//        }
//        else {
//            httpResponse.sendRedirect("/LJKZOJ-1.0-SNAPSHOT/competitions.xhtml");
//        }

        chain.doFilter(request, response);
    }
}
