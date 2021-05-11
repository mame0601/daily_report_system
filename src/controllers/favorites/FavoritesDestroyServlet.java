package controllers.favorites;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favorite;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsCreateServlet
 */
@WebServlet(urlPatterns={"/favorites/destroy"})
public class FavoritesDestroyServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoritesDestroyServlet()
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

                Favorite myFavorite = new Favorite();
                try
                {
                    myFavorite = em.createNamedQuery("getMyFavorites", Favorite.class)
                            .setParameter("employee_id", Integer.parseInt(request.getParameter("my_id")))
                            .setParameter("report_id", Integer.parseInt(request.getParameter("reports_id")))
                            .getSingleResult();
                }
                catch(Exception _e) {}

                if(myFavorite != null)
                {
                    System.out.println("データ削除");
                    em.getTransaction().begin();
                    em.remove(myFavorite);  // データ削除
                    em.getTransaction().commit();
                }
                em.close();
            }
        }
        catch(Exception e) {}
    }

}
