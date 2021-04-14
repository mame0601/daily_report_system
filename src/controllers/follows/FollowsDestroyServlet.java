package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsCreateServlet
 */
@WebServlet(urlPatterns={"/follows/destroy"})
public class FollowsDestroyServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsDestroyServlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String _token = request.getParameter("_token");

            if(_token != null && _token.equals(request.getSession().getId()))
            {
                EntityManager em = DBUtil.createEntityManager();

                Follow myFollow = new Follow();
                try
                {
                    myFollow = em.createNamedQuery("getOneMyFollows", Follow.class)
                            .setParameter("my_code", request.getParameter("my_code"))
                            .setParameter("follows_code", request.getParameter("follows_code"))
                            .getSingleResult();
                }
                catch(Exception e) {}

                if(myFollow.getMy_code() != null)
                {
                    System.out.println("データ削除");
                    em.getTransaction().begin();
                    em.remove(myFollow);  // データ削除
                    em.getTransaction().commit();
                    em.close();
                }
            }
        }
        catch(Exception e) {}
    }

}
