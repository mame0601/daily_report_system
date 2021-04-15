package controllers.follows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/follows/index")
public class FollowsIndexServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsIndexServlet()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EntityManager em = DBUtil.createEntityManager();

        // ログインしている従業員のEmployeeオブジェクトを取得
        Employee _e = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try
        {
            page = Integer.parseInt(request.getParameter("page"));
        }
        catch(Exception e)
        {
            page = 1;
        }

        // 取得したい日報情報結果リストの変数を用意
        List<Report> followingReportsResult = new ArrayList<Report>();

        // フォロー管理テーブルに自分の社員番号で検索をする（ログインしている人のフォロー人数レコードを取得する）
        List<Follow> follows = em.createNamedQuery("getMyFollows", Follow.class)
                .setParameter("my_code", _e.getCode())
                .getResultList();

        // 取得したフォロー情報を繰り返して日報情報を取得
        for(Follow follow : follows)
        {
            // フォロー情報のフォローされている社員番号をもとにレポートテーブルを検索
            List<Report> followingReports = em.createNamedQuery("getAllTimelineReports", Report.class)
                    .setParameter("follows_code", follow.getFollows_code())
                    .getResultList();

            // 取得した情報を followingReportsResult に追加
            followingReportsResult.addAll(followingReports);
        }

        // 取得結果を格納したリストをソート
        followingReportsResult = followingReportsResult.stream()
                .sorted(Comparator.comparing(Report::getUpdated_at).reversed())
                .collect(Collectors.toList());

        List<Report> viewReport = new ArrayList<Report>();

        int _i = 15 * (page -1);
        while(_i < followingReportsResult.size())
        {
            if(_i > 15 * (page -1) + 15)    break;
            viewReport.add(followingReportsResult.get(_i));
            _i++;
        }

        long reports_count = (long)followingReportsResult.size();

        em.close();

        request.setAttribute("reports", viewReport);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null)
        {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/timeline/index.jsp");
        rd.forward(request, response);
    }

}
