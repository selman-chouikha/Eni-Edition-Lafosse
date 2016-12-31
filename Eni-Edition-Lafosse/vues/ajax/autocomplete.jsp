<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>

<c:forEach items="${liste}" var="ligne" >
		<c:out value="${ligne}"/>
</c:forEach>


