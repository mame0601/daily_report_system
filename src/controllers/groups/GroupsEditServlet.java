package controllers.groups;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeEditServlet
 */
@WebServlet("/groups/edit")
public class GroupsEditServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupsEditServlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EntityManager em = DBUtil.createEntityManager();

        Group g = em.find(Group.class, request.getParameter("id"));

        em.close();

        request.setAttribute("group", g);
        request.setAttribute("_token", request.getSession().getId());
        request.getSession().setAttribute("group_id", g.getId());
        request.setAttribute("group_id_show", 1);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/edit.jsp");
        rd.forward(request, response);
    }

}
