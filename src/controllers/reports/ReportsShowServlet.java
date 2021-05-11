package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Favorite;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet()
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

        // いいね件数を取得
        long favorites_count = (long)em.createNamedQuery("getMyReportsFavoritesCount", Long.class)
                    .setParameter("report", r.getId())
                    .getSingleResult();

        // 自分がいいね済みの日報か確認する
        Favorite myFavorite = new Favorite();
        Employee e = (Employee)request.getSession().getAttribute("login_employee");
        try
        {
            myFavorite = em.createNamedQuery("getMyFavorites", Favorite.class)
                    .setParameter("employee_id", e.getId())
                    .setParameter("report_id", r.getId())
                    .getSingleResult();
        }
        catch(Exception _e) {}
        em.close();

        request.setAttribute("report", r);
        request.setAttribute("favorites_count", favorites_count);
        request.setAttribute("_token", request.getSession().getId());

        if(myFavorite.getReport_id() != null)
        {
            request.setAttribute("myFavorite", 1);
        }
        else
        {
            request.setAttribute("myFavorite", null);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
