<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name ="content">

        <c:choose>
            <c:when test="${group != null}">
                <h2>グループ詳細画面</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>グループID</th>
                            <td>　<c:out value="${group.id}" /></td>
                        </tr>
                        <tr>
                            <th>グループ名</th>
                            <td>　<c:out value="${group.name}" /></td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${employee.admin_flag == 1 }">
                    <p><a href="<c:url value='/groups/edit?id=${group.id}' />">このグループ情報を編集する</a></p>
                </c:if>


                <br /><br /><br />
                <h2>グループに所属する人　一覧</h2>

                <table id="employee_list">
                    <tbody>
                        <tr>
                            <th>ユーザーID</th>
                            <th>ユーザー名</th>
                            <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                                <th>削除</th>
                            </c:if>
                        </tr>

                        <c:forEach var="groupuser" items="${groupusers}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td>
                                    <c:out value="${groupuser.employee.id}" />
                                </td>
                                <td>
                                    <c:out value="${groupuser.employee.name}" />
                                </td>
                                <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                                    <td>
                                        <a href="#" onclick="confirmDestroy();">✖</a>
                                        <form method="POST" action="<c:url value='/groupusers/destroy' />">
                                            <input type="hidden" name="_token" value="${_token}" />
                                            <input type="hidden" name="id" value="${groupuser.employee.id }">
                                            <input type="hidden" name="group_id" value="${groupuser.group_id }">
                                        </form>
                                        <script>
                                            function confirmDestroy()
                                            {
                                                if(confirm("本当に削除してよろしいですか？"))
                                                {
                                                    document.forms[0].submit();
                                                }
                                            }
                                        </script>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>

                <div id="pagination">

                    （全 ${groupusers_count} 件）<br />
                    <c:forEach var ="i" begin="1" end="${((groupusers_count -1) / 15) + 1 }" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/groups/index?page=${i}' />"><c:out value="${i}" /></a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                </div>

                <c:if test="${employee.admin_flag == 1 }">
                    <p><a href="<c:url value='/groupusers/new?id=${group.id}' />">ユーザー所属追加</a></p>
                </c:if>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>


        <p><a href="<c:url value='/groups/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>