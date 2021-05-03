<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="id">ユーザー</label><br />
<select name="user">
    <option value="0">-- ユーザーを選択して下さい --</option>
    <c:forEach var="id_emptyuser" items="${id_emptyusers}">
            <option value="${id_emptyuser.id}">${id_emptyuser.name}</option>
    </c:forEach>
</select>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<input type="hidden" name="id" value="${id}" />
<button type="submit">登録</button>