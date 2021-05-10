<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<br />
<input type="hidden" name="_token" value="${_token}" />
<input type="hidden" name="report_id" value="<c:out value="${report.id}" />" />
<button type="submit">この日報を承認する</button>