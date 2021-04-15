package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsShowServlet
 */
@WebServlet("/follows/show")
public class FollowsShowServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsShowServlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Employee _e = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try
        {
            page = Integer.parseInt(request.getParameter("page"));
        }
        catch(Exception e)
        {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", r.getEmployee())
                .setFirstResult(15 * (page -1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", r.getEmployee())
                .getSingleResult();

        Follow myFollow = new Follow();

        try
        {
            myFollow = em.createNamedQuery("getOneMyFollows", Follow.class)
                    .setParameter("my_code", _e.getCode())
                    .setParameter("follows_code", r.getEmployee().getCode())
                    .getSingleResult();
        }
        catch(Exception e) {}

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("_token", request.getSession().getId());

        if(myFollow.getMy_code() != null)
        {
            System.out.println("データあり");
            request.setAttribute("myFollow", 1);
        }
        else
        {
            System.out.println("データなし");
            request.setAttribute("myFollow", null);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/timeline/show.jsp");
        rd.forward(request, response);
    }

}
