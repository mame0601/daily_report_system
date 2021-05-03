package controllers.groups;

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
import models.Group;
import models.GroupUser;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet("/groups/show")
public class GroupsShowServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupsShowServlet()
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

        Employee e = (Employee)request.getSession().getAttribute("login_employee");
        Group g = em.find(Group.class, request.getParameter("id"));


        // グループに所属する人一覧（index部分）
        int page = 1;
        try
        {
            page = Integer.parseInt(request.getParameter("page"));
        }
        catch(NumberFormatException _e) {}

        List <GroupUser> groupusers = em.createNamedQuery("getMyAllGroupUsers", GroupUser.class)
                .setParameter("group_id", g.getId())
                .setFirstResult(15 * (page -1))
                .setMaxResults(15)
                .getResultList();

        long groupusers_count = (long)em.createNamedQuery("getMyGroupUsersCount", Long.class)
                .setParameter("group_id", g.getId())
                .getSingleResult();

        em.close();

        // show部分
        request.setAttribute("employee", e);
        request.setAttribute("group", g);

        // group_users部分
        request.setAttribute("groupusers", groupusers);
        request.setAttribute("groupusers_count", groupusers_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null)
        {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        System.out.println("test");
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/show.jsp");
        rd.forward(request, response);
    }

}
