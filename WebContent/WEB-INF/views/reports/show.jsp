<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <c:choose>
            <c:when test="${report != null }">
                <h2 class="favorite_h2">日報　詳細ページ</h2>&nbsp;&nbsp;&nbsp;

                <c:if test="${sessionScope.login_employee.id != report.employee.id }">
                    <c:choose>
                        <c:when test="${myFavorite == null }">
                            <button type="button" id="btn-a" value="follow">いいね</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" id="btn-a" value="follow">いいね解除</button>
                        </c:otherwise>
                    </c:choose>
                    <br /><br />
                </c:if>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name }" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date }" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at }" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at }" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div id="favorite">いいね数：${favorites_count}</div>

                <c:if test="${sessionScope.login_employee.id == report.employee.id }">
                    <p><a href="<c:url value="/reports/edit?id=${report.id }" />">この日報を編集する</a></p>
                </c:if>
                <c:if test="${!report.approval && sessionScope.login_boss.group_id == report.employee.group_id && sessionScope.login_employee.id != report.employee.id }">
                    <form method="POST" action="<c:url value="/approvals/update" />">
                        <c:import url="_approvalsform.jsp" />
                    </form>
                </c:if>
            </c:when>
            <c:otherwise>
               <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>

        <input type="hidden" id="_token" value="${_token }" />
        <input type="hidden" id="my_id" value="${sessionScope.login_employee.id }" />
        <input type="hidden" id="reports_id" value="${report.id }" />
        <input type="hidden" id="favorites_count" value="${favorites_count }" />
        <script>
            $(document).on('click','#btn-a', function()
            {
                // いいねボタンをクリックしたイベントをキャッチする
                // いいねボタンの表示文字を取得
                var text = $("#btn-a").text();
                // ボタンの表示文字を判定する
                if (text == "いいね") {
                // いいねの場合、未登録として扱う
                    createFavorite();
                } else {
                // いいねでない場合、登録済みとして扱う
                    destroyFavorite();
                }
            });

            function createFavorite()
            {
                // hiddenタグのトークンを取得
                var _token = $("#_token").val();
                var my_id = $("#my_id").val();
                var reports_id = $("#reports_id").val();
                var pram =
                {
                    '_token' : _token,
                    'my_id' : my_id,
                    'reports_id' : reports_id
                };

                //ajaxでservletにリクエストを送信
                $.ajax
                ({
                    type  : "POST",
                    url   : "/daily_report_system/favorites/create",
                    data  : pram,
                    async : true,
                    success : function(data)
                    {
                        // buttonタグで表示している文字を変更します
                        $("#btn-a").text("いいね解除");
                        var fav = $("#favorites_count").val();
                        fav++;
                        $("#favorite").html("いいね数：" + fav);
                        $("#favorites_count").attr('value', fav);
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        alert("リクエスト時に何らかのエラーが発生しました：" + textStatus + ":\n" + errorThrown);
                    }
                });
            }
            function destroyFavorite()
            {
                // hiddenタグのトークンを取得
                var _token = $("#_token").val();
                var my_id = $("#my_id").val();
                var reports_id = $("#reports_id").val();
                var pram =
                {
                    '_token' : _token,
                    'my_id' : my_id,
                    'reports_id' : reports_id
                };

                //ajaxでservletにリクエストを送信
                $.ajax
                ({
                    type  : "POST",
                    url   : "/daily_report_system/favorites/destroy",
                    data  : pram,
                    async : true,
                    success : function(data)
                    {
                        // buttonタグで表示している文字を変更します
                        $("#btn-a").text("いいね");
                        var fav = $("#favorites_count").val();
                        fav--;
                        $("#favorite").html("いいね数：" + fav);
                        $("#favorites_count").attr('value', fav);
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        alert("リクエスト時に何らかのエラーが発生しました：" + textStatus + ":\n" + errorThrown);
                    }
                });
            }
        </script>



    </c:param>
</c:import>