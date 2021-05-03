<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>ユーザー所属追加画面</h2>

        <form method="POST" action="<c:url value='/groupusers/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='/groups/show?id=${id}' />">詳細画面に戻る</a></p>
        <p><a href="<c:url value='/groups/index' />">一覧に戻る</a></p>

    </c:param>
</c:import>