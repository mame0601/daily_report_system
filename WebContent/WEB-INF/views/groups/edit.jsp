<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <c:choose>
            <c:when test="${group != null}">
                <h2>グループID : ${group.id} のグループ情報　編集ページ</h2>
                <form method="POST" action="<c:url value='/groups/update' />">
                    <c:import url="_formedit.jsp" />
                </form>

                <p><a href="#" onclick="confirmDestroy();">このグループを削除する</a></p>
                <form method="POST" action="<c:url value='/groups/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy()
                    {
                        if(confirm("本当に削除してよろしいですか？"))
                        {
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/groups/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>