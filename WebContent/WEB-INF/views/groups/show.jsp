<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name ="content">

        <c:choose>
            <c:when test="${group != null}">
                <h2>グループID : ${group.id} のグループ詳細画面</h2>

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
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>


        <p><a href="<c:url value='/groups/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>