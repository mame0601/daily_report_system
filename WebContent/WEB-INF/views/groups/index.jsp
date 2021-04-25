<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>グループ　一覧</h2>

        <table id="employee_list">
            <tbody>
                <tr>
                    <th>グループID</th>
                    <th>グループ名</th>
                    <th>操作</th>
                </tr>

                <c:forEach var="group" items="${groups}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>
                            <c:out value="${group.id}" />
                        </td>
                        <td>
                            <c:out value="${group.name}" />
                        </td>
                        <td>
                            <a href="<c:url value='/groups/show?id=${group.id}' />">詳細を表示</a>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <div id="pagination">

            （全 ${groups_count} 件）<br />
            <c:forEach var ="i" begin="1" end="${((groups_count -1) / 15) + 1 }" step="1">
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
        <c:if test="${sessionScope.login_employee.admin_flag == 1 }">
            <p><a href="<c:url value="/groups/new" />">新規グループの作成</a></p>
        </c:if>

    </c:param>
</c:import>