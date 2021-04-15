<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">

        <c:choose>
            <c:when test="${report != null }">
                <h2>従業員　詳細ページ</h2>
                <h3 class="follow_h3"><c:out value="${report.employee.name }" /></h3>&nbsp;&nbsp;&nbsp;

                <c:choose>
                    <c:when test="${myFollow == null }">
                        <button type="button" id="btn-a" value="follow" class="follow_button">フォロー</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" id="btn-a" value="follow" class="follow_button">フォロー中</button>
                    </c:otherwise>
                </c:choose>

                <br /><br />

                <h4>【<c:out value="${report.employee.name }" />さんの日報　一覧】</h4>

                <table id="report_list">
                    <tbody>
                        <tr>
                            <th class="report_name">氏名</th>
                            <th class="report_date">日付</th>
                            <th class="report_title">タイトル</th>
                            <th class="report_action">操作</th>
                        </tr>
                        <c:forEach var="report" items="${reports }" varStatus="status">
                            <tr class="row${status.count % 2 }">
                                <td class="report_name"><c:out value="${report.employee.name}" /></td>
                                <td class="report_date"><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                                <td class="report_title">${report.title}</td>
                                <td class="report_action"><a href="<c:url value="/reports/show?id=${report.id}" />">詳細を見る</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div id="pagination">
                （全 ${reports_count} 件）<br />
                <c:forEach var="i" begin="1" end="${((reports_count -1) / 15) + 1}" step="1">
                    <c:choose>
                        <c:when test="${i == page }">
                            <c:out value="${i }" />&nbsp;
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='/follows/show?id=${report.id}&page=${i }' />"><c:out value="${i}" /></a>&nbsp;
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            </c:when>
            <c:otherwise>
               <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>

        <input type="hidden" id="_token" value="${_token }" />
        <input type="hidden" id="my_code" value="${sessionScope.login_employee.code }" />
        <input type="hidden" id="follows_code" value="${report.employee.code }" />
        <script>
            $(document).on('click','#btn-a', function()
            {
                // フォローボタンをクリックしたイベントをキャッチする
                // フォローボタンの表示文字を取得
                var text = $("#btn-a").text();
                // ボタンの表示文字を判定する
                if (text == "フォロー") {
                // フォローの場合、未登録として扱う
                    createFollow();
                } else {
                // フォローでない場合、登録済みとして扱う
                    destroyFollow();
                }
            });

            function createFollow()
            {
                // hiddenタグのトークンを取得
                var _token = $("#_token").val();
                var my_code = $("#my_code").val();
                var follows_code = $("#follows_code").val();
                var pram =
                {
                    '_token' : _token,
                    'my_code' : my_code,
                    'follows_code' : follows_code
                };

                //ajaxでservletにリクエストを送信
                $.ajax
                ({
                    type  : "POST",
                    url   : "http://localhost:8080/daily_report_system/follows/create",
                    data  : pram,
                    async : true,
                    success : function(data)
                    {
                        // buttonタグで表示している文字を変更します
                        $("#btn-a").text("フォロー中");
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        alert("リクエスト時に何らかのエラーが発生しました：" + textStatus + ":\n" + errorThrown);
                    }
                });
            }
            function destroyFollow()
            {
                // hiddenタグのトークンを取得
                var _token = $("#_token").val();
                var my_code = $("#my_code").val();
                var follows_code = $("#follows_code").val();
                var pram =
                {
                    '_token' : _token,
                    'my_code' : my_code,
                    'follows_code' : follows_code
                };

                //ajaxでservletにリクエストを送信
                $.ajax
                ({
                    type  : "POST",
                    url   : "http://localhost:8080/daily_report_system/follows/destroy",
                    data  : pram,
                    async : true,
                    success : function(data)
                    {
                        // buttonタグで表示している文字を変更します
                        $("#btn-a").text("フォロー");
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