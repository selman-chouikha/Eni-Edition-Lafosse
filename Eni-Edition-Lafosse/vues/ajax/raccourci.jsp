<%@ page contentType="application/xml;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>


<xml version="1.0" encoding="ISO-8859-1">
	<raccourcis>
		<code valeur="<c:out value="${codeErreur}"/>"></code>
		
		<c:forEach items="${liste}" var="raccourci" >
			<raccourci id="<c:out value='${raccourci.id}'/>" url="<c:out value='${raccourci.url}'/>" nom="<c:out value='${raccourci.nom}'/>" />
		</c:forEach>
	
	</raccourcis>
</xml>