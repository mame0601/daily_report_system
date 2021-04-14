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
@WebServlet(urlPatterns={"/follows/create"})
public class FollowsCreateServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsCreateServlet()
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

                Follow f = new Follow();

                f.setMy_code(request.getParameter("my_code"));
                f.setFollows_code(request.getParameter("follows_code"));

                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                em.close();

                /*
                // JSONマップ
                Map<String, String> mapMsg = new HashMap<String, String>();

                // マッパー
                ObjectMapper mapper = new ObjectMapper();

                // JSON文字列
                String jsonStr = mapper.writeValueAsString(mapMsg);

                // ヘッダー設定
                response.setContentType("application/json;charset=UTF-8");

                // PrintWriterオブジェクト
                PrintWriter pw = response.getWriter();
                pw.print(jsonStr);
                pw.close();
                */
            }
        }
        catch(Exception e) {}
    }

}
