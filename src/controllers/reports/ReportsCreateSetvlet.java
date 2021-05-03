package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;
import validators.ReportValidator;

/**
 * Servlet implementation class ReportsCreateSetvlet
 */
@WebServlet("/reports/create")
public class ReportsCreateSetvlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateSetvlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId()))
        {
            EntityManager em = DBUtil.createEntityManager();

            Report r = new Report();

            r.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            if(rd_str != null && !rd_str.equals(""))
            {
                report_date = Date.valueOf(request.getParameter("report_date"));
            }
            r.setReport_date(report_date);

            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            // 承認／未承認チェック
            if(r.getEmployee().getAdmin_flag() == 1)
            {
                // 管理者の場合、自動的に承認済みにする
                r.setApproval(true);
            }
            else
            {
                // 一般の場合未承認
                r.setApproval(false);
            }

            List<String> errors = ReportValidator.validate(r);
            if(errors.size() > 0)
            {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);
            }
            else
            {
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/reports/index");
            }




        }
    }

}
