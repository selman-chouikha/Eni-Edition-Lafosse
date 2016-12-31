<%@ page contentType="application/xml;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>


<xml version="1.0" encoding="ISO-8859-1">
	<articles>
		<article note="<c:out value='${note}'/>" vote="<c:out value='${vote}'/>" autorisation="<c:out value='${autorisation}'/>" />
	</articles>
</xml>