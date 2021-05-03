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
import validators.GroupUserValidator;

/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet("/groupusers/create")
public class GroupUsersCreateServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupUsersCreateServlet()
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

            GroupUser g = new GroupUser();
            Employee _e = em.find(Employee.class, Integer.parseInt(request.getParameter("user")));

            // ユーザー未選択のエラー処理
            if(Integer.parseInt(request.getParameter("user")) == 0)
            {
                List <Employee> id_emptyusers = em.createNamedQuery("getEmpryGroupIdAllEmployees", Employee.class)
                        .setParameter("group_id", request.getParameter("id"))
                        .setFirstResult(0)
                        .setMaxResults(999)
                        .getResultList();

                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("groupuser", g);
                request.setAttribute("errors", "ユーザーが選択されていません。");
                request.setAttribute("id", request.getParameter("id"));
                request.setAttribute("id_emptyusers", id_emptyusers);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groupusers/new.jsp");
                rd.forward(request, response);
            }
            else
            {
                g.setGroup_id(request.getParameter("id"));
                g.setEmployee(_e);

                // 既に同じユーザーが登録されていないかチェック
                List<String> errors = GroupUserValidator.validate(g, true);
                if(errors.size() > 0)
                {
                    List <Employee> id_emptyusers = em.createNamedQuery("getEmpryGroupIdAllEmployees", Employee.class)
                            .setParameter("group_id", request.getParameter("id"))
                            .setFirstResult(0)
                            .setMaxResults(999)
                            .getResultList();

                    em.close();

                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("groupuser", g);
                    request.setAttribute("errors", errors);
                    request.setAttribute("id", request.getParameter("id"));
                    request.setAttribute("id_emptyusers", id_emptyusers);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groupusers/new.jsp");
                    rd.forward(request, response);
                }
                else
                {
                    em.getTransaction().begin();
                    em.persist(g);
                    em.getTransaction().commit();
                    request.getSession().setAttribute("flush", "登録が完了しました。");
                    em.close();

                    response.sendRedirect(request.getContextPath() + "/groups/index");
                }
            }
        }
    }

}
