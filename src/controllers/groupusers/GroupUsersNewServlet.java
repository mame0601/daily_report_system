package controllers.groupusers;

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
import models.GroupUser;
import utils.DBUtil;

/**
 * Servlet implementation class GroupUsersNewServlet
 */
@WebServlet("/groupusers/new")
public class GroupUsersNewServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupUsersNewServlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
     // グループ詳細画面（show）部分
        EntityManager em = DBUtil.createEntityManager();

        List <Employee> id_emptyusers = em.createNamedQuery("getEmpryGroupIdAllEmployees", Employee.class)
                .setParameter("group_id", request.getParameter("id"))
                .setFirstResult(0)
                .setMaxResults(999)
                .getResultList();

        em.close();

        request.setAttribute("id_emptyusers", id_emptyusers);

        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("groupuser", new GroupUser());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groupusers/new.jsp");
        rd.forward(request, response);
    }

}
