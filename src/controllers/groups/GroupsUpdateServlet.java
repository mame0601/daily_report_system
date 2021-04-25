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

import models.Group;
import utils.DBUtil;
import validators.GroupValidator;

/**
 * Servlet implementation class EmployeeUpdateServlet
 */
@WebServlet("/groups/update")
public class GroupsUpdateServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupsUpdateServlet()
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

            Group g = em.find(Group.class, request.getSession().getAttribute("group_id"));
            Boolean idDuplicateCheckFlag = false;

            g.setName(request.getParameter("name"));

            List<String> errors = GroupValidator.validate(g, idDuplicateCheckFlag);
            if(errors.size() > 0)
            {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("group", g);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/edit.jsp");
                rd.forward(request, response);
            }
            else
            {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("group_id");

                response.sendRedirect(request.getContextPath() + "/groups/index");
            }
        }
    }

}
