package controllers.groupusers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.GroupUser;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet("/groupusers/destroy")
public class GroupUsersDestroyServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupUsersDestroyServlet()
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

            Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            GroupUser g = em.createNamedQuery("checkRegisteredUserId", GroupUser.class)
                    .setParameter("employee", e)
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(g);  // データ削除
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "削除が完了しました。");
            em.close();

            response.sendRedirect(request.getContextPath() + "/groups/show?id=" + request.getParameter("group_id"));
        }
    }
}
