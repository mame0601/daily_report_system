package controllers.unapproved;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.GroupUser;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/unapproved/index")
public class UnapprovedIndexServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnapprovedIndexServlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EntityManager em = DBUtil.createEntityManager();

        GroupUser login_boss = (GroupUser)request.getSession().getAttribute("login_boss");

        int page;
        try
        {
            page = Integer.parseInt(request.getParameter("page"));
        }
        catch(Exception e)
        {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getAllUnapprovedReports", Report.class)
                .setParameter("group_id", login_boss.getGroup_id())
                .setFirstResult(15 * (page -1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long)em.createNamedQuery("getUnapprovedReportsCount", Long.class)
                .setParameter("group_id", login_boss.getGroup_id())
                .getSingleResult();

        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null)
        {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/unappproved/index.jsp");
        rd.forward(request, response);
    }

}
